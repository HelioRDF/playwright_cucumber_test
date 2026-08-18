@echo off
cd /d "%~dp0"
npx playwright codegen --target java --channel chrome https://web.whatsapp.com
