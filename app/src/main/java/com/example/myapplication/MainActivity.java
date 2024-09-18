package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int randomNumber; // Случайное число ..
    private int attemptsLeft = 3; // Количество попыток
    private int minRange = 1; // Минимум диапазона
    private int maxRange = 100; // Максимум диапазона
    private EditText guessInput;
    private TextView resultText;
    private TextView attemptsText;
    private TextView rangeText;
    private TextView targetNumberText; // Новый TextView для отображения загаданного числа

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessInput = findViewById(R.id.guessInput);
        resultText = findViewById(R.id.resultText);
        attemptsText = findViewById(R.id.attemptsText);
        rangeText = findViewById(R.id.rangeText);
        targetNumberText = findViewById(R.id.targetNumberText); // Инициализация нового TextView
        Button checkButton = findViewById(R.id.checkButton);

        // Генерация случайного числа при запуске
        resetGame();

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
    }

    // Метод для проверки введенного числа
    private void checkGuess() {
        String guessString = guessInput.getText().toString();

        // Проверка, что ввод не пустой
        if (guessString.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, введите число", Toast.LENGTH_SHORT).show();
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(guessString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Пожалуйста, введите корректное число", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка числа
        if (guess > randomNumber) {
            maxRange = guess - 1;
            resultText.setText("Меньше!");
        } else if (guess < randomNumber) {
            minRange = guess + 1;
            resultText.setText("Больше!");
        } else {
            resultText.setText("Поздравляю! Вы угадали число.");
            Toast.makeText(this, "Вы выиграли! Начните новую игру!", Toast.LENGTH_LONG).show();
            resetGame();
            return;
        }

        // Обновляем диапазон
        rangeText.setText("Диапазон: " + minRange + " - " + maxRange);

        // Уменьшаем количество попыток
        attemptsLeft--;
        attemptsText.setText("Осталось попыток: " + attemptsLeft);

        // Если попытки закончились, сбрасываем игру
        if (attemptsLeft == 0) {
            Toast.makeText(this, "Игра окончена! Число было: " + randomNumber, Toast.LENGTH_LONG).show();
            resetGame();
        }
    }

    // Метод для сброса игры (генерация нового случайного числа)
    private void resetGame() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1; // Случайное число от 1 до 100
        attemptsLeft = 3; // Сброс количества попыток
        minRange = 1; // Сброс диапазона
        maxRange = 100; // Сброс диапазона

        // Сброс текста
        attemptsText.setText("Осталось попыток: " + attemptsLeft);
        rangeText.setText("Диапазон: " + minRange + " - " + maxRange);
        resultText.setText("");
        guessInput.setText("");
        targetNumberText.setText("Число для угадывания: " + randomNumber); // Показываем число для угадывания
    }
}
