package com.manish.login_register_qr.modal

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

fun generateQrFromInputs(
    empID: Int?, firstName: String, middleName: String,
    lastName: String, phoneNo: String, email: String
): Bitmap {

    val qrData = """
        Emp ID: $empID
        First Name: $firstName
        Middle Name: $middleName
        Last Name: $lastName
        Phone: $phoneNo
        Email: $email
    """.trimIndent()

    return generateQrCode(qrData)
}

fun generateQrCode(text: String, size: Int = 512): Bitmap {
    val hints = hashMapOf<EncodeHintType, Any>().apply {
        put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H)
        put(EncodeHintType.MARGIN, 2)
    }

    val bitMatrix = QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hints)

    val bitmap = createBitmap(size, size, Bitmap.Config.RGB_565)
    val canvas = Canvas(bitmap)
    canvas.drawColor(Color.WHITE)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    val cellSize = size.toFloat() / bitMatrix.width

    for (x in 0 until bitMatrix.width) {
        for (y in 0 until bitMatrix.height) {
            if (bitMatrix[x, y]) {
                val left = x * cellSize
                val top = y * cellSize
                canvas.drawRect(left, top, left + cellSize, top + cellSize, paint)
            }
        }
    }

    return bitmap
}