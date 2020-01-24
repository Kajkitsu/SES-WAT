package pl.edu.wat.seswat.ui.teacher

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.spartons.qrcodegeneratorreader.ScanQrCodeActivity
import kotlinx.android.synthetic.main.activity_scan_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pl.edu.wat.seswat.QRCodeQenerator.QRCodeHelper
import pl.edu.wat.seswat.R

class ShowQRCodeActivity : AppCompatActivity() {


    lateinit var barcodeBackImageView: ImageView
    lateinit var qrCodeView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_show_qr_code)
        barcodeBackImageView = findViewById(R.id.barcode_back_image_view)
        barcodeBackImageView.setOnClickListener { onBackPressed() }
        qrCodeView = findViewById(R.id.qr_code_view)

        val code = intent.getStringExtra("code")
        val bitmap = QRCodeHelper.newInstance(this).setContent(code).setErrorCorrectionLevel(ErrorCorrectionLevel.Q).setMargin(2).qrcOde

        qrCodeView.setImageBitmap(bitmap)







    }
}


