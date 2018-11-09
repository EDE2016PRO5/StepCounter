
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
            //If you want to continuously track the number of steps over a long period of time,
            // do NOT unregister for this sensor, so that it keeps counting steps in the background
            // even when the AP is in suspend mode and report the aggregate count
            // when the AP is awake. Application needs to stay registered for this sensor
            // because step counter does not count steps if it is not activated
            mSensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
            println("""name: ${stepSensor.name} maxdelay: ${stepSensor.maxDelay}
                |   maximum range: ${stepSensor.maximumRange}
                |   isDynamicSensor: ${stepSensor.isDynamicSensor}
                |   isWakeUpSensor: ${stepSensor.isWakeUpSensor}
                |   power ${stepSensor.power} reportingMode ${stepSensor.reportingMode}
                |   resolution ${stepSensor.resolution} type: """.trimMargin())
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