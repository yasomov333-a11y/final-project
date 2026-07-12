@echo off
setlocal

cd /d "%~dp0"

where javac >nul 2>nul
if errorlevel 1 (
    echo [Помилка] javac не знайдено. Встанови JDK 17+ ^(наприклад, з adoptium.net^) і спробуй ще раз.
    pause
    exit /b 1
)

echo Компіляція проєкту...
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
if errorlevel 1 (
    echo [Помилка] Компіляція не вдалася. Дивись повідомлення вище.
    del sources.txt
    pause
    exit /b 1
)
del sources.txt

echo Запуск програми...
echo.
java -cp out university.Main

echo.
pause
