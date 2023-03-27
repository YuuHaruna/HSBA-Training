package com.beetech.hsba.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.base.entity.BaseObjectResponse
import com.beetech.hsba.databinding.HomeFragmentBinding
import com.beetech.hsba.entity.LoginResponse
import com.beetech.hsba.entity.SSOEntity
import com.beetech.hsba.entity.SSOPlatform
import com.beetech.hsba.extension.toast
import com.beetech.hsba.utils.AsteriskPasswordTransformationMethod
import com.beetech.hsba.utils.showHidePass
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: HomeFragmentBinding? = null

    private val binding get() = _binding!!

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var googleLoginForResult: ActivityResultLauncher<Intent>

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleLoginForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null) {
                try {
                    val acc = GoogleSignIn.getSignedInAccountFromIntent(it.data).getResult(ApiException::class.java)
                    toast("Login with Google success by account ${acc.email} and result code is ${it.resultCode}")
                } catch (e: ApiException) {
                    Log.d("Login", "signInResult:failed code=" + e.statusCode)
                    if (it.resultCode != Activity.RESULT_CANCELED) toast("Login with Google fail: ${e.statusCode} and result code is ${it.resultCode}")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun backFromAddFragment() {
        
    }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initGoogleSSO()

        initFacebookSSO()

        val ssoData = listOf(
            SSOEntity(R.drawable.zalo_logo, "Zalo", SSOPlatform.Zalo),
            SSOEntity(R.drawable.facebook_logo, "Facebook", SSOPlatform.Facebook),
            SSOEntity(R.drawable.apple_logo, "Apple", SSOPlatform.Apple),
            SSOEntity(R.drawable.google_logo, "Google", SSOPlatform.Google),
        )

        binding.recyclerViewLoginSso.apply {
            adapter = SSORecyclerViewAdapter(ssoData, object : OnClickSSO {
                override fun loginSSO(type: SSOPlatform) {
                    when (type) {
                        SSOPlatform.Google -> googleSignIn()

                        SSOPlatform.Facebook -> facebookSignIn()

                        else -> {
                            googleSignInClient.signOut()
                            googleSignInClient.revokeAccess()
                        }
                    }
                }
            })
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.editTextLoginPassword.transformationMethod = AsteriskPasswordTransformationMethod()

        binding.imageButtonLoginShowHidePass.setOnClickListener {
            showHidePass(requireContext(), binding.editTextLoginPassword, binding.imageButtonLoginShowHidePass)
        }

        binding.buttonLoginLoginButton.setOnClickListener { viewModel.login() }

        viewModel.loginStateFlow.observe(viewLifecycleOwner){
            handleObjectResponse(it)
        }
    }

    private fun initGoogleSSO() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent

        googleLoginForResult.launch(signInIntent)
    }

    private fun initFacebookSSO() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    toast("Login with Facebook success")
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(error: FacebookException) {
                    toast("Login with Facebook fail: ${error.message}")
                    Log.d("Login", "${error.message}")
                }
            })
    }


    private fun facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(
            this,
            callbackManager,
            listOf("public_profile")
        )
    }
    override fun <U> getObjectResponse(data: U) {
        super.getObjectResponse(data)
        if(data is LoginResponse) toast(data.accessToken ?: "")
    }

    override fun initView() {
        
    }

    override fun initData() {
        
    }

    override fun initListener() {
        
    }

    override fun backPressed(): Boolean {

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}