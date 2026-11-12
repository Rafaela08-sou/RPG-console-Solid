#!/bin/sh
# Compila e executa o RPG em console (Linux/macOS).
# Uso: ./executar.sh
set -e
cd "$(dirname "$0")"
rm -rf out
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out rpg.Main
