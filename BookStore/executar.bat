@echo off
chcp 65001 > nul
cls
echo ======================================================================
echo   BookStore Enterprise • Compilando e Executando o Projeto
echo   Sistema Operacional: Windows 10 / 11
echo ======================================================================
echo.

where javac >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERRO] O compilador javac nao foi encontrado no PATH do Windows!
    pause
    exit /b 1
)

if not exist "bin" mkdir bin

echo [1/2] Compilando as classes Java do projeto BookStore...
javac -encoding UTF-8 -cp "lib/*" -d bin src/br/com/bookstore/model/*.java src/br/com/bookstore/factory/*.java src/br/com/bookstore/dao/*.java src/br/com/bookstore/main/*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha durante a compilacao.
    pause
    exit /b 1
)

echo [2/2] Compilacao concluida com sucesso!
echo.
echo Executando o sistema...
echo ======================================================================
java -cp "bin;lib/*" br.com.bookstore.main.Main

echo.
pause
