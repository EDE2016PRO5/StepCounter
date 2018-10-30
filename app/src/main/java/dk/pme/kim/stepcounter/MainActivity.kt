
package dk.pme.kim.stepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener
{
    private var mSensorManager:SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  Setup the sensors:
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null)
        {
            Toast.makeText(this, "No Step Counter sensor was found!",
                    Toast.LENGTH_LONG).show()
        }

        else
        {
            mSensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }

    }

    override fun onSensorChanged(event: SensorEvent)
    {
        txtWalk.text = """${event.values[0].toInt()}"""
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int)
    {
    }
}