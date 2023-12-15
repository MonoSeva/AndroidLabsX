package com.example.appnanrj;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// класс реализует интерфейс SensorEventListener,
// который содержит методы onSensorChanged и onAccuracyChanged для обработки событий сенсоров
// SensorEventListener используется для регистрации слушателей сенсоров и обработки событий изменения данных сенсоров
// код использует Android-сенсоры для отслеживания изменений магнитного поля и ускорения устройства
public class HelloActivity1 extends AppCompatActivity implements SensorEventListener {

    private ImageView gradRotation; // ImageView для отображения изображения, которое будет поворачиваться
    private TextView infText; // TextView для отображения текущего азимута и направления

    //SensorManager - это класс, который предоставляет доступ к системным сенсорам
    //в коде он используется для получения доступа к сенсорам магнитного поля и ускорения
    private SensorManager sensorManager; // объект SensorManager для работы с системными сенсорами
    private float azimuthCurrent = 0f; // текущий азимут
    private float[] mGravity; // массив значений ускорения
    private float[] mGeomagnetic; // массив значений магнитного поля
    private String[] directions = {  // массив направлений
            "СЕВЕР",
            "СЕВЕРО-ВОСТОК",
            "ВОСТОК",
            "ЮГО-ВОСТОК",
            "ЮГ",
            "ЮГО-ЗАПАД",
            "ЗАПАД",
            "СЕВЕРО-ЗАПАД"};

    // вызывается при создании активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact1);
        gradRotation = findViewById(R.id.gradRotation);
        infText = findViewById(R.id.infText);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // менеджер, который управляет сенсорами
    }

    // onSensorChanged вызывается при изменении значений сенсоров магнитного поля и ускорения.
    // в методе происходит вычисление текущего азимута с помощью метода getRotationMatrix() и getOrientation() из класса SensorManager
    // также происходит определение текущего направления (север, запад, юг, восток и т.д.) с помощью метода getDirection()
    @Override
    public void onSensorChanged(SensorEvent event) {
        float azimuth; // текущий азимут
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) { // если событие от сенсора ускорения
            mGravity = event.values; // сохраняем значения ускорения в массив
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) { // если событие от сенсора магнитного поля
            mGeomagnetic = event.values; // сохраняем значения магнитного поля в массив
        }
        if (mGravity != null && mGeomagnetic != null) { // если есть значения ускорения и магнитного поля
            float R[] = new float[9]; // матрица поворота
            float I[] = new float[9]; // матрица поворота
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic); // получаем матрицу поворота
            if (success) { // если удалось получить матрицу поворота
                float orientation[] = new float[3]; // массив ориентации
                SensorManager.getOrientation(R, orientation); // получаем ориентацию
                azimuth = (float) Math.toDegrees(orientation[0]); // получаем текущий азимут
                azimuth = (azimuth + 360) % 360; // азимут приводится к диапазону от 0 до 360 градусов

                // определяется направление по текущему азимуту
                int directionIndex = (int) Math.round(azimuth / 45) % 8;
                String direction = directions[directionIndex]; // получаем направление по индексу

                infText.setText(Math.round(azimuth) + " град. " + direction); // отображаем текущий азимут и направление в TextView

                RotateAnimation rotateAnimation = new RotateAnimation( // создаем анимацию поворота изображения
                        azimuthCurrent, // начальный угол поворота
                        -azimuth, // конечный угол поворота
                        Animation.RELATIVE_TO_SELF, // кручение относительно центра (самого себя)
                        0.5f,
                        Animation.RELATIVE_TO_SELF, // кручение относительно центра (самого себя)
                        0.5f);
                rotateAnimation.setDuration(210); // задаем длительность анимации
                rotateAnimation.setFillAfter(true); // сохраняем состояние после анимации

                gradRotation.startAnimation(rotateAnimation); // запуск анимации поворота изображения

                azimuthCurrent = -azimuth; // сохраняем текущий азимут
            }
        }
    }

    // не используем
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // вызывается при паузе активности (при закрытии активности)
    // и отменяет регистрацию слушателей сенсоров
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this); // отменяем регистрацию слушателей сенсоров при паузе активности
    }

    // вызывается при возобновлении активности
    // и регистрирует слушателей для сенсоров ускорения и магнитного поля с заданной частотой обновления данных
    @Override
    protected void onResume() {
        super.onResume();
        // регистрация слушателей сенсоров:
        sensorManager.registerListener(
                this, // это активити
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), // регистрируем слушатель для сенсора ускорения
                SensorManager.SENSOR_DELAY_NORMAL); // задаем частоту обновления данных
        sensorManager.registerListener(
                this, // это активити
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), // регистрируем слушатель для сенсора магнитного поля
                SensorManager.SENSOR_DELAY_NORMAL); // задаем частоту обновления данных
    }
}
