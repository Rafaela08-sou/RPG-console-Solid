@echo off
REM Compila e executa o RPG em console (Windows).
REM Uso: executar.bat
chcp 65001 > nul
cd /d "%~dp0"
if exist out rmdir /s /q out
mkdir out
dir /s /b src\*.java > fontes.txt
javac -encoding UTF-8 -d out @fontes.txt
if errorlevel 1 goto fim
java -cp out rpg.Main
:fim
del fontes.txt
pause
