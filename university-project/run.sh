#!/bin/bash
# Компілює та запускає University Console App.
# Подвійний клік у Finder / файловому менеджері (якщо дозволено запуск
# як застосунку) або запуск з терміналу: ./run.sh

cd "$(dirname "$0")" || exit 1

if ! command -v javac &> /dev/null; then
    echo "[Помилка] javac не знайдено. Встанови JDK 17+ і спробуй ще раз."
    read -p "Натисни Enter, щоб закрити..."
    exit 1
fi

echo "Компіляція проєкту..."
if ! javac -d out $(find src -name "*.java"); then
    echo "[Помилка] Компіляція не вдалася. Дивись повідомлення вище."
    read -p "Натисни Enter, щоб закрити..."
    exit 1
fi

echo "Запуск програми..."
echo
java -cp out university.Main

echo
read -p "Натисни Enter, щоб закрити..."
