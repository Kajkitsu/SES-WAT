package com.spartons.qrcodegeneratorreader

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import pl.edu.wat.seswat.R

class ScanQrCodeActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    companion object {
        private const val HUAWEI = "huawei"
        private const val MY_CAMERA_REQUEST_CODE = 6515
        fun getScanQrCodeActivity(callingClassContext: Context) = Intent(callingClassContext, ScanQrCodeActivity::class.java)
    }

    lateinit var barcodeBackImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_scan_qr_code)
        setScannerProperties()
        barcodeBackImageView = findViewById(R.id.barcodeBackImageView)
        barcodeBackImageView.setOnClickListener { onBackPressed() }
        flashOnOffImageView.setOnClickListener {
            if (qrCodeScanner.flash) {
                qrCodeScanner.flash = false
                flashOnOffImageView.background = ContextCompat.getDrawable(this, R.drawable.flash_off_vector_icon)
            } else {
                qrCodeScanner.flash = true
                flashOnOffImageView.background = ContextCompat.getDrawable(this, R.drawable.flash_on_vector_icon)
            }
        }
    }

    /**
     * Set bar code scanner basic properties.
     */

    private fun setScannerProperties() {
        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)
        if (Build.MANUFACTURER.equals(HUAWEI, ignoreCase = true))
            qrCodeScanner.setAspectTolerance(0.5f)
    }

    /**
     * resume the qr code camera when activity is in onResume state.
     */

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                        MY_CAMERA_REQUEST_CODE)
                return
            }
        }
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }

    /**
     * To check if user grant camera permission then called openCamera function.If not then show not granted
     * permission snack bar.
     *
     * @param requestCode  specify which request result came from operating system.
     * @param permissions  to specify which permission result is came.
     * @param grantResults to check if user granted the specific permission or not.
     */

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()
            else if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                showCameraSnackBar()
        }
    }

    @SuppressLint("ResourceType")
    private fun showCameraSnackBar() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            val snackbar = Snackbar.make(scanQrCodeRootView, "Kamera potrzebuje twoich uprawnien w celu zeskanowania QR kodu", Snackbar.LENGTH_LONG)
            val view1 = snackbar.view
            view1.setBackgroundColor(ContextCompat.getColor(this, Color.WHITE))
//            val textView = view1.findViewById<TextView>(R.id.snackbar_text)
//            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            snackbar.show()
        }
    }

    private fun openCamera() {
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }

    /**
     * stop the qr code camera scanner when activity is in onPause state.
     */

    override fun onPause() {
        super.onPause()
        qrCodeScanner.stopCamera()
    }

    override fun handleResult(p0: Result?) {
        if (p0 != null) {
            //startActivity(ScannedActivity.getScannedActivity(this, p0.text))
            Toast.makeText(this,p0.text,Toast.LENGTH_LONG).show()
            var returnIntent = Intent()
            returnIntent.putExtra("result",p0.text)
            setResult(Activity.RESULT_OK,returnIntent)
            finish()

            resumeCamera()
        }
    }

    /**
     * Resume the camera after 2 seconds when qr code successfully scanned through bar code reader.
     */

    private fun resumeCamera() {
        Toast.LENGTH_LONG
        val handler = Handler()
        handler.postDelayed({ qrCodeScanner.resumeCameraPreview(this@ScanQrCodeActivity) }, 2000)
    }

}
