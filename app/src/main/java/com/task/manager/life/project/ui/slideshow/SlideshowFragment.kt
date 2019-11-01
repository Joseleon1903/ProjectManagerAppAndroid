package com.task.manager.life.project.ui.slideshow

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.task.manager.life.project.R
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel

    private var mContext: Context? = null
    private var mActivity: Activity? = null

    private var mCLayout: ConstraintLayout? = null
    private var mButtonDo: Button? = null
    private var mProgressDialog: ProgressBar? = null
    private var mImageView: ImageView? = null
    private var mImageViewInternal: ImageView? = null

    private var mMyTask: AsyncTask<*, *, *>? = null

    companion object {
        internal const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        // Get the application context
        mContext = this.requireContext()
        mActivity = this@SlideshowFragment.activity

        // Get the widget reference from XML layout
        mCLayout = root.findViewById(R.id.constraintLayout) as ConstraintLayout
        mButtonDo = root.findViewById(R.id.btn_do) as Button
        mImageView = root.findViewById(R.id.iv) as ImageView
        mImageViewInternal = root.findViewById(R.id.iv_internal) as ImageView

        // Initialize the progress dialog
        mProgressDialog = ProgressBar(this.mContext, null, android.R.attr.progressBarStyleLarge)

        // Initialize a new click listener for positive button widget
        mButtonDo!!.setOnClickListener {
            // Execute the async task
            mMyTask = DownloadTask()
                .execute(
                    stringToURL(
                        "https://doc-0k-bk-docs.googleusercontent.com/docs/securesc/auhrtqsvav6n71ptplm6flrj2d6j54q6/p0t4okuv0c92dsbabnvg3fs4usobmv4v/1572026400000/09786362980406590161/09786362980406590161/1tYfCX3W2GeN9U4NHLTGIYZgpPXUWW73Q"
                    )
                )
        }

//        permissionsRequestLocation()

        return root
    }

    private fun permissionsRequestLocation() : Boolean {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_NETWORK_STATE)
            } != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.ACCESS_NETWORK_STATE),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
                return false
            }

        }
        return true
    }


    private inner class DownloadTask : AsyncTask<URL, Void, Bitmap>() {
        // Before the tasks execution
        override fun onPreExecute() {
            // Display the progress dialog on async task start
            mProgressDialog!!.visibility = View.VISIBLE
        }

        // Do the task in background/non UI thread
        override fun doInBackground(vararg urls: URL): Bitmap? {
            val url = urls[0]
            var connection: HttpURLConnection? = null

            try {
                // Initialize a new http url connection
                connection = url.openConnection() as HttpURLConnection

                // Connect the http url connection
                connection.connect()

                // Get the input stream from http url connection
                val inputStream = connection.getInputStream()
                Log.d(TAG, "read input stream stream ..")

                /*
                    BufferedInputStream
                        A BufferedInputStream adds functionality to another input stream-namely,
                        the ability to buffer the input and to support the mark and reset methods.
                */
                /*
                    BufferedInputStream(InputStream in)
                        Creates a BufferedInputStream and saves its argument,
                        the input stream in, for later use.
                */
                // Initialize a new BufferedInputStream from InputStream
                val bufferedInputStream = BufferedInputStream(inputStream)
                Log.d(TAG, "read bufferr stream ..")

                /*
                    decodeStream
                        Bitmap decodeStream (InputStream is)
                            Decode an input stream into a bitmap. If the input stream is null, or
                            cannot be used to decode a bitmap, the function returns null. The stream's
                            position will be where ever it was after the encoded data was read.

                        Parameters
                            is InputStream : The input stream that holds the raw data
                                              to be decoded into a bitmap.
                        Returns
                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
                */
                // Convert BufferedInputStream to Bitmap object

                // Return the downloaded bitmap
                return BitmapFactory.decodeStream(bufferedInputStream)

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                // Disconnect the http url connection
                connection!!.disconnect()
            }
            return null
        }

        // When all async task done
        override fun onPostExecute(result: Bitmap?) {
            // Hide the progress dialog
            mProgressDialog!!.visibility = View.GONE

            if (result != null) {
                // Display the downloaded image into ImageView
                mImageView!!.setImageBitmap(result)

                // Save bitmap to internal storage
                val imageInternalUri = saveImageToInternalStorage(result)
                // Set the ImageView image from internal storage
                mImageViewInternal!!.setImageURI(imageInternalUri)
            } else {
                // Notify user that an error occurred while downloading image
                Toast.makeText(this@SlideshowFragment.mContext, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Custom method to convert string to url
    protected fun stringToURL(urlString: String): URL {
        try {
            return URL(urlString)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return URL("")
    }

    // Custom method to save a bitmap into internal storage
    protected fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        // Initialize ContextWrapper
        val wrapper = ContextWrapper(this.requireContext())

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("Images", MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, "UniqueFileName" + ".jpg")

        try {
            // Initialize a new OutputStream
            var stream: OutputStream?

            // If the output file exists, it can be replaced or appended to it
            stream = FileOutputStream(file)

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flushes the stream
            stream.flush()

            // Closes the stream
            stream.close()

        } catch (e: IOException) // Catch the exception
        {
            e.printStackTrace()
        }

        // Parse the gallery image url to uri

        // Return the saved image Uri
        return Uri.parse(file.absolutePath)
    }


}