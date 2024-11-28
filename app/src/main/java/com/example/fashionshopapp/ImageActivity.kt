package com.example.fashionshopapp

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import java.io.IOException

class ImageActivity: ComponentActivity() {

    // Bat dau them
    private lateinit var selectedImageView: ImageView
    private lateinit var confirmButton: Button
    private var detectedLabels: ArrayList<String>? = null
    // Ket thuc them

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            displaySelectedImage(uri)

            processImageUri(uri)
        } else {
            Toast.makeText(this, "Không chọn được ảnh!", Toast.LENGTH_SHORT).show()

            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun displaySelectedImage(uri: Uri){
        selectedImageView.setImageURI(uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        // Ánh xạ các View
        selectedImageView = findViewById(R.id.selectedImage)
        confirmButton = findViewById(R.id.confirmButton)


        // Yêu cầu chọn ảnh khi activity được khởi chạy
        imagePickerLauncher.launch("image/*")

        // Xử lý khi nhấn nút Xác nhận
        confirmButton.setOnClickListener {
            // Thay doi ne
            if (detectedLabels.isNullOrEmpty()) {
                Toast.makeText(this, "Chưa có nhãn phân tích!", Toast.LENGTH_SHORT).show()
            } else {
                val resultIntent = Intent().apply {
                    putStringArrayListExtra("detectedLabels", detectedLabels)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Đóng activity và trả kết quả
            }

            /*Toast.makeText(this, "Nhấn xác nhận để gửi kết quả!", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()*/
        }
    }

    private fun processImageUri(uri: Uri) {
        try {
            val image = InputImage.fromFilePath(this, uri)
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    //bo val o day
                    detectedLabels = ArrayList(labels.map { it.text })
                    //Moi ne (xem loi)
                    Log.d("ImageActivity", "Labels detected: $detectedLabels")
                    //Moi tiep ne
                    Toast.makeText(this, "Phân tích hoàn tất. Nhấn xác nhận để tiếp tục.", Toast.LENGTH_SHORT).show()

                    /*val resultIntent = Intent().apply {
                        putStringArrayListExtra("detectedLabels", detectedLabels)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)*/
                    //finish()
                }
                .addOnFailureListener { e ->
                    Log.e("ImageActivity", "Error processing image", e)
                    //Moi nua ne
                    Toast.makeText(this, "Lỗi phân tích ảnh!", Toast.LENGTH_SHORT).show()

                    //setResult(Activity.RESULT_CANCELED)
                    //finish()
                }
                //Moi
                /*.addOnCompleteListener{
                    finish()
                }*/
        } catch (e: IOException) {
            Log.e("ImageActivity", "Lỗi đọc ảnh: ", e)
            Toast.makeText(this, "Lỗi xử lý ảnh!", Toast.LENGTH_SHORT).show()
            /*setResult(Activity.RESULT_CANCELED)
            finish()*/
        }
    }
}