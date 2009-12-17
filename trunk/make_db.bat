@echo off
cls
echo Criando o banco de dados
mysql -u root -p -e "create database webpd"
echo Povoando o banco de dados
mysql -u root -p webpd < webpd_db.txt
echo Concluido.
pause