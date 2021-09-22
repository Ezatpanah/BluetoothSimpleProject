package com.ezatpanah.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_DISCOVERABLE_BT: Int = 1
    private val REQUEST_CODE_ENABLE_BT: Int = 1

    lateinit var bAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bAdapter == null) {
            blts_sts.text = "Bluetooth is Not available"
        } else {
            blts_sts.text = "Bluetooth is available  "
        }


        if (bAdapter.isEnabled) {
            blts_img.setImageResource(R.drawable.ic_bluetooth_on)
        } else {
            blts_img.setImageResource(R.drawable.ic_bluetooth_off)

        }


        btn_on.setOnClickListener {
            if (bAdapter.isEnabled) {
                Toast.makeText(this, "Already ON", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
                blts_img.setImageResource(R.drawable.ic_bluetooth_on)

            }
        }


        btn_off.setOnClickListener {
            if (!bAdapter.isEnabled) {
                Toast.makeText(this, "Already OFF", Toast.LENGTH_LONG).show()
            } else {
                bAdapter.disable()
                blts_img.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this, "Bluetooth Turned OFF", Toast.LENGTH_LONG).show()

            }
        }


        btn_discvr.setOnClickListener {
            if (bAdapter.isDiscovering) {
                Toast.makeText(this, "Making Your device discoverable", Toast.LENGTH_LONG).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)
            }
        }

        btn_getprddvs.setOnClickListener {
            if(bAdapter.isEnabled){
                prdsts.text="Paired devices"
                val devices=bAdapter.bondedDevices
                for(device in devices){
                    val deviceName= device.name
                    val deviceAddress= device
                    prdsts.append("\nDevice: $deviceName , $device")
                }

            }
            else {
                Toast.makeText(this, "Turn on bluetooth first", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth is ON", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Could not ON Bluetooth", Toast.LENGTH_LONG).show()
                }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}