package com.beetech.hsba.ui.chat

import android.Manifest
import android.animation.ValueAnimator
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.*
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.beetech.hsba.R
import com.beetech.hsba.base.BaseFragment
import com.beetech.hsba.databinding.FragmentChatBinding
import com.beetech.hsba.extension.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class ChatFragment : BaseFragment() {
    override fun backFromAddFragment() {

    }

    override fun initView() {
        binding.chatParentView.setOnClickListener { it.hideKeyboard() }

        binding.textViewChatVoiceChatTime.stop()

        val tv = TypedValue()
        val actionBarHeight =
            if (requireContext().theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
            } else 0
        binding.toolBarChatFragment.layoutParams.height = requireContext().statusBarHeight() + actionBarHeight
//        bottomSheetGalleryBehavior = BottomSheetBehavior.from(binding.bottomSheetGalleryImage.parentViewBottomSheetGalleryImage)

//        binding.recyclerViewChat.apply {
//            setPadding(this.paddingLeft, headerHeight, this.paddingRight, this.paddingBottom)
//        }
//        setupBottomSheetGallery(headerHeight)

        ViewCompat.setOnApplyWindowInsetsListener(binding.chatParentView) { v, insets ->
            val animator = ValueAnimator.ofInt(0, insets.getInsets(WindowInsetsCompat.Type.ime()).bottom)
            animator.addUpdateListener {
                    valueAnimator -> v.setPadding(0, 0, 0, valueAnimator.animatedValue as? Int ?: 0)
            }
            animator.duration = 50
            animator.start()
            insets
        }

        binding.recyclerViewChat.apply {
            adapter = ChatListAdapter()
            layoutManager = LinearLayoutManager(requireContext()).apply {
                stackFromEnd = true
            }
            itemAnimator = null
        }
    }

    override fun initData() {
        binding.viewModel = viewModel

        val bluetoothReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent
            ) {
                val action = intent.action
                if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                    toast("Đã kết nối vào răng xanh")
                } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED == action) {
                    toast("Đã ngắt kết nối tới răng xanh")
                }
            }
        }

        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        requireContext().registerReceiver(bluetoothReceiver, filter)
    }

    override fun initListener() {

    }

    override fun backPressed(): Boolean {

        return true
    }

    override val layoutId: Int
        get() = R.layout.fragment_chat

    private val viewModel: ChatViewModel by viewModels()

    private var _binding: FragmentChatBinding? = null

    private val binding get() = _binding!!

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private var mediaRecorder: MediaRecorder? = null

//    private lateinit var openCameraForResult: ActivityResultLauncher<Intent>
//
//    private lateinit var bottomSheetGalleryBehavior: BottomSheetBehavior<ConstraintLayout>

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                hideBottomSheetGallery()
//            }
//        })
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    checkPermission {

                    }
                }
            }

//        openCameraForResult =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                if (it.resultCode == Activity.RESULT_OK && it.data != null) {
//                    val img = it.data?.extras?.get("data") as Bitmap
//                    val imgUri = getImageUri(img).toString()
//
//                    viewModel.sendImg(listOf(imgUri))
//                }
//            }
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listMessage.observe(viewLifecycleOwner) {listMsg ->
            Looper.getMainLooper()?.let {
                Handler(it).postDelayed({
                    if (listMsg.isNotEmpty()) {
                        binding.recyclerViewChat.smoothScrollToPosition(listMsg.size)
                    }
                },500)
            }
        }

        binding.layoutChatTextChatBox.imageButtonChatSend.setOnClickListener {
            viewModel.sendMsg()
//            viewModel.selectedGalleryImage.value?.apply {
//                if (this.isNotEmpty()) {
//                    viewModel.sendImg(this)
//                    hideBottomSheetGallery()
//                    viewModel.initListGalleryImageUIModel(getAllGalleryImage())
//                }
//            }
            it.hideKeyboard()
        }

//        binding.imageButtonChatNavBack.setOnClickListener {
//            findNavController().popBackStack()
//            changeTopAppBar()
//        }

//        binding.imageButtonChatTakePhoto.setOnClickListener {
//            checkPermission { takePhoto() }
//        }
//
//        binding.imageButtonChatGallery.setOnClickListener {
//            checkPermission { showBottomSheetGallery() }
//        }

        var fileVoice = ""
        binding.layoutChatTextChatBox.imageButtonChatVoiceChat.setOnClickListener {
            it.hideKeyboard()
            checkPermission {
                fileVoice = "${requireContext().externalCacheDir?.absolutePath}/recording_" + Calendar.getInstance().timeInMillis + ".m4a"
                binding.groupChatVoiceChatBox.visible()
                binding.layoutChatTextChatBox.root.invisible()
                binding.recyclerViewChatWaveAnimation.apply {
                    adapter = ListWaveVoiceChatAdapter(requireContext())
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    itemAnimator = null
                }

                (binding.recyclerViewChatWaveAnimation.adapter as ListWaveVoiceChatAdapter).apply {
                    repeat(10){ addModel(10, false) }
                }
                startVoiceChat(fileVoice)
            }
        }

        binding.imageButtonChatCancelVoiceChat.setOnClickListener {
            binding.groupChatVoiceChatBox.gone()
            binding.layoutChatTextChatBox.root.visible()
            stopVoiceChat()
        }

        binding.imageButtonChatResetVoiceChat.setOnClickListener {
            resetVoiceChat(fileVoice)
        }

        binding.imageButtonChatSendVoiceChat.setOnClickListener {
            binding.groupChatVoiceChatBox.gone()
            binding.layoutChatTextChatBox.root.visible()
            sendVoiceChat(fileVoice)
        }

//        viewModel.selectedGalleryImage.observe(viewLifecycleOwner) {
//            if (it.isNotEmpty()) {
//                binding.bottomSheetGalleryImage.textViewBottomSheetImageGalleryImageCount.text = it.size.toString()
//                binding.bottomSheetGalleryImage.textViewBottomSheetImageGalleryImageCount.visibility = View.VISIBLE
//                binding.bottomSheetGalleryImage.imageButtonBottomSheetImageGalleryExpanseSend.isEnabled = true
//            } else {
//                binding.bottomSheetGalleryImage.textViewBottomSheetImageGalleryImageCount.visibility = View.GONE
//                binding.bottomSheetGalleryImage.imageButtonBottomSheetImageGalleryExpanseSend.isEnabled = false
//            }
//        }

//        binding.imageButtonChatCollapseGalleryBottomSheet.setOnClickListener {
//            hideBottomSheetGallery()
//        }
    }

//    private fun changeTopAppBar() {
//        binding.toolBarChatFragment.visibility = View.GONE
//
//        activity?.findViewById<AppBarLayout>(R.id.topAppBar_mainActivity)?.visibility = View.VISIBLE
//
//        activity?.findViewById<BottomNavigationView>(R.id.bottomNav_activityMain_bottomNav)?.visibility = View.VISIBLE
//    }

//    private fun takePhoto() {
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        openCameraForResult.launch(cameraIntent)
//    }

    private fun setupBottomSheetGallery(headerHeight: Int) {
        bottomSheetGalleryBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        val bottomSheetBinding = binding.bottomSheetGalleryImage

        bottomSheetGalleryBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) { }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val slideOff = if (slideOffset < 0) 0f else slideOffset

                val paramHeader = binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryExpanseHeader.layoutParams
                paramHeader.height = (headerHeight * slideOff).toInt().inc()
                binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryExpanseHeader.layoutParams = paramHeader

                val paramIndicator = binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.layoutParams
                paramIndicator.height = (20.dpToPx(requireContext()) * (1 - slideOff)).toInt().inc()
                binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.layoutParams = paramIndicator

                if (slideOff == 1f) {
                    binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.visibility = View.GONE
                    binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryExpanseSendBox.visibility = View.VISIBLE
                    if (viewModel.selectedGalleryImage.value.isNullOrEmpty())
                        binding.bottomSheetGalleryImage.textViewBottomSheetImageGalleryImageCount.visibility = View.GONE
                } else {
                    binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.visibility = View.VISIBLE
                    binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryExpanseSendBox.visibility = View.GONE
                }

                if (slideOff == 0f) binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryHeader.visibility = View.GONE
                else binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryHeader.visibility = View.VISIBLE
            }
        })

        bottomSheetGalleryBehavior.peekHeight = requireContext().resources.displayMetrics.heightPixels * 2 / 5

        bottomSheetBinding.viewModel = viewModel

//    private fun setupBottomSheetGallery(headerHeight: Int) {
//        bottomSheetGalleryBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//
//        val bottomSheetBinding = binding.bottomSheetGalleryImage
//
//        bottomSheetGalleryBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) { }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                val slideOff = if (slideOffset < 0) 0f else slideOffset
//
//                val paramHeader = binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryExpanseHeader.layoutParams
//                paramHeader.height = (headerHeight * slideOff).toInt().inc()
//                binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryExpanseHeader.layoutParams = paramHeader
//
//                val paramIndicator = binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.layoutParams
//                paramIndicator.height = (20.dpToPx(requireContext()) * (1 - slideOff)).toInt().inc()
//                binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.layoutParams = paramIndicator
//
//                if (slideOff == 1f) {
//                    binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.visibility = View.GONE
//                    binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryExpanseSendBox.visibility = View.VISIBLE
//                    if (viewModel.selectedGalleryImage.value.isNullOrEmpty())
//                        binding.bottomSheetGalleryImage.textViewBottomSheetImageGalleryImageCount.visibility = View.GONE
//                } else {
//                    binding.bottomSheetGalleryImage.viewBottomSheetImageGalleryIndicator.visibility = View.VISIBLE
//                    binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryExpanseSendBox.visibility = View.GONE
//                }
//
//                if (slideOff == 0f) binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryHeader.visibility = View.GONE
//                else binding.bottomSheetGalleryImage.groupBottomSheetImageGalleryHeader.visibility = View.VISIBLE
//            }
//        })
//
//        bottomSheetGalleryBehavior.peekHeight = requireContext().resources.displayMetrics.heightPixels * 2 / 5
//
//        bottomSheetBinding.viewModel = viewModel
//
//        bottomSheetBinding.recyclerViewGalleryImage.apply {
//            adapter = GalleryImageListAdapter(object : OnClickGalleryImageRecyclerView {
//                override fun onClickGalleryImage(position: Int) {
//                    viewModel.onClickGalleryImage(position)
//                }
//            })
//            layoutManager = GridLayoutManager(requireContext(), 3)
//            addItemDecoration(
//                MarginItemDecorationGridLayout(
//                    1.dpToPx(requireContext()),
//                    3
//                )
//            )
//        }
//
//        bottomSheetBinding.imageButtonBottomSheetImageGalleryExpanseHeaderClose.setOnClickListener {
//            hideBottomSheetGallery()
//        }
//
//        bottomSheetBinding.imageButtonBottomSheetImageGalleryExpanseSend.setOnClickListener {
//            if (viewModel.listGalleryImageUIModel.value?.isNotEmpty() == true) {
//                viewModel.sendImg(viewModel.selectedGalleryImage.value?.toList() ?: listOf(" "))
//                hideBottomSheetGallery()
//            }
//        }
//
//        viewModel.initListGalleryImageUIModel(getAllGalleryImage())
//    }

//    private fun hideBottomSheetGallery() {
//        bottomSheetGalleryBehavior.isHideable = true
//        bottomSheetGalleryBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//        val param = binding.viewChatChatBox.layoutParams as ViewGroup.MarginLayoutParams
//        param.setMargins(0)
//        binding.groupChatChatBoxNotOpenGallery.visibility = View.VISIBLE
//        binding.imageButtonChatCollapseGalleryBottomSheet.visibility = View.GONE
//    }
//
//    private fun showBottomSheetGallery() {
//        bottomSheetGalleryBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        bottomSheetGalleryBehavior.isHideable = false
//        val param = binding.viewChatChatBox.layoutParams as ViewGroup.MarginLayoutParams
//        param.setMargins(0, 0, 0, requireContext().resources.displayMetrics.heightPixels * 2 / 5)
//        binding.groupChatChatBoxNotOpenGallery.visibility = View.GONE
//        binding.imageButtonChatCollapseGalleryBottomSheet.visibility = View.VISIBLE
//        viewModel.initListGalleryImageUIModel(getAllGalleryImage())
//    }

//    private fun getAllGalleryImage(): List<String> {
//
//        val listImageURI = mutableListOf<String>()
//
//        val projection = arrayOf(MediaStore.Images.Media._ID)
//        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
//        val selection: String? = null
//        val selectionArgs: Array<String>? = null
//
//        requireContext().contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            selection,
//            selectionArgs,
//            sortOrder
//        )?.use { cursor ->
//            while (cursor.moveToNext()) {
//                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
//                val imageUri =
//                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
//                listImageURI.add(imageUri.toString())
//            }
//        }
//
//        return listImageURI
//    }

//    private fun getImageUri(img: Bitmap): Uri? {
//        val bytes = ByteArrayOutputStream()
//        img.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//        val path: String = MediaStore.Images.Media.insertImage(
//            requireContext().contentResolver,
//            img,
//            "Title",
//            null
//        )
//        return Uri.parse(path)
//    }

    private fun startVoiceChat(file: String){
        mediaRecorder = MediaRecorder()
        mediaRecorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(file)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            try {
                prepare()
            } catch (e: IOException) {
                Log.e("ChatFrag", "Prepare() failed")
            }
            start()
        }
        binding.textViewChatVoiceChatTime.base = SystemClock.elapsedRealtime()
        binding.textViewChatVoiceChatTime.start()
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                while (mediaRecorder != null){
                    withContext(Dispatchers.Main){
                        (binding.recyclerViewChatWaveAnimation.adapter as ListWaveVoiceChatAdapter).addModel(
                            mediaRecorder?.maxAmplitude,
                            true
                        )
                        Log.d("VoiceAni", mediaRecorder?.maxAmplitude.toString())
                    }
                    delay(100)
                }
            }
        }
    }

    private fun stopVoiceChat(){
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        binding.textViewChatVoiceChatTime.stop()
    }

    private fun resetVoiceChat(file: String){
        mediaRecorder?.apply {
            reset()
        }
        binding.textViewChatVoiceChatTime.stop()
        startVoiceChat(file)
    }

    private fun sendVoiceChat(file: String){
        if (mediaRecorder == null) return
        viewModel.senVoiceChat(file)
        stopVoiceChat()
    }

    private fun checkPermission(onPermissionGranted: () -> Unit) {
        when {
            isPermissionGranted() -> {
                onPermissionGranted()
            }
//            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> Toast.makeText(
//                requireContext(),
//                "This app need camera permission to take photo",
//                Toast.LENGTH_SHORT
//            ).show()
//
//            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> Toast.makeText(
//                requireContext(),
//                "This app need access to phone storage to open gallery",
//                Toast.LENGTH_SHORT
//            ).show()

            shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> toast("This app need record audio permission to send voice message")

            else -> {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        REQUIRED_PERMISSIONS.forEach { requestPermissionLauncher.launch(it) }
    }

    private fun isPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
//            arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
            arrayOf(Manifest.permission.RECORD_AUDIO)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}