package com.beetech.hsba.ui.chat

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beetech.hsba.entity.BaseMessage
import com.beetech.hsba.entity.Message
import com.beetech.hsba.entity.MessageType
import com.beetech.hsba.entity.VoiceMessage

class ChatViewModel : ViewModel() {

    val chatMessage = MutableLiveData<String>()

    private val _listMessage = MutableLiveData<List<BaseMessage>>(listOf())
    val listMessage: LiveData<List<BaseMessage>> get() = _listMessage

//    private val _listGalleryImageUIModel = MutableLiveData<List<GalleryImageUIModel>>(listOf())
//    val listGalleryImageUIModel: LiveData<List<GalleryImageUIModel>> get() = _listGalleryImageUIModel
//
//    private val _selectedGalleryImage = MutableLiveData<List<String>>(mutableListOf())
//    val selectedGalleryImage: LiveData<List<String>> get() = _selectedGalleryImage

    fun sendMsg() {
        if (!chatMessage.value.isNullOrEmpty()) {
            val temp = listMessage.value?.toMutableList()
            temp?.add(Message(chatMessage.value!!).apply { setMsgType(MessageType.Send) })
            _listMessage.value = temp?.toList()

            Looper.getMainLooper()?.let {
                Handler(it).postDelayed({
                    temp?.add(
                        Message(
                            "\"Xin chào bạn. Rất vui làm quen với bạn\""
                        ).apply { setMsgType(MessageType.Receive) }
                    )
                    _listMessage.value = temp?.toList()
                }, 200)
            }

            chatMessage.value = ""
        }
    }

    fun senVoiceChat(file: String){
        if (file.isBlank()) return
        val temp = listMessage.value?.toMutableList()
        temp?.add(VoiceMessage(file).apply { setMsgType(MessageType.SendVoice) })
        _listMessage.value = temp?.toList()
    }

//    fun sendImg(img: List<String>) {
//        val temp = listMessage.value?.toMutableList()
//        if (img.size > 1) temp?.add(ImageMessage(img).apply { setMsgType(MessageType.SendMultiImg) })
//        else temp?.add(ImageMessage(img).apply { setMsgType(MessageType.SendImg) })
//        _listMessage.value = temp?.toList()
//        _selectedGalleryImage.value = listOf()
//    }
//
//    fun initListGalleryImageUIModel(listImage: List<String>) {
//        val listUiModel = mutableListOf<GalleryImageUIModel>()
//        listImage.forEach {
//            listUiModel.add(GalleryImageUIModel(it, false))
//        }
//        _listGalleryImageUIModel.value = listUiModel.toList()
//    }
//
//    fun onClickGalleryImage(position: Int) {
//        val temp = _selectedGalleryImage.value?.toMutableList()
//        if (listGalleryImageUIModel.value?.get(position)?.selected == true) {
//            _listGalleryImageUIModel.value?.get(position)?.selected = false
//            temp?.remove(_listGalleryImageUIModel.value?.get(position)?.image ?: " ")
//        } else {
//            _listGalleryImageUIModel.value?.get(position)?.selected = true
//            temp?.add(_listGalleryImageUIModel.value?.get(position)?.image ?: " ")
//        }
//        _selectedGalleryImage.value = temp?.toList()
//    }
}