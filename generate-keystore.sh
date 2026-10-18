#!/usr/bin/env bash
# Crée votre clé de signature. À faire UNE seule fois, puis SAUVEGARDER le .jks et les mots de passe.
# Si vous les perdez, vous ne pourrez plus publier de mises à jour de l'app.
set -e
keytool -genkeypair -v -keystore tepg-release.jks -alias tepg \
  -keyalg RSA -keysize 2048 -validity 10000
echo
echo "Pour GitHub Actions, encodez le fichier en base64 :"
echo "  base64 -w0 tepg-release.jks > tepg-release.jks.b64   (Linux)"
echo "  base64 -i tepg-release.jks | tr -d '\n' > tepg-release.jks.b64   (macOS)"
