# TEPG – App Android (Devis & Clients)

Enveloppe Android (WebView) autour de l'app web `app/src/main/assets/index.html`.
Les données (clients, devis) restent sur le téléphone.

## Obtenir l'APK

### Option A : Android Studio (PC)
1. Ouvrir ce dossier dans Android Studio, attendre la synchronisation Gradle.
2. Menu **Build > Build APK(s)**.
3. L'APK est dans `app/build/outputs/apk/debug/app-debug.apk`. Le copier sur le téléphone.

### Option B : GitHub (sans installer Android Studio)
1. Créer un dépôt GitHub et y envoyer tout le contenu de ce dossier (dossier `.github` compris).
2. Onglet **Actions > Build APK > Run workflow**.
3. Une fois terminé, télécharger l'artefact **TEPG-apk** (zip contenant `app-debug.apk`).

## Installer
Ouvrir `app-debug.apk` sur le téléphone et autoriser « Installer des applications inconnues » si demandé.

## Modifier l'app
Éditer `app/src/main/assets/index.html` (numéro, couleurs, devise dans Réglages dans l'app), puis régénérer l'APK.

## Version release signée

1. **Créer la clé** (une seule fois) : `./generate-keystore.sh` (nécessite le JDK). Sauvegardez `tepg-release.jks` et les mots de passe en lieu sûr : sans eux, plus de mises à jour possibles.
2. **Build local** : copier `keystore.properties.example` en `keystore.properties`, le remplir, puis `gradle assembleRelease`.
   Sortie : `app/build/outputs/apk/release/app-release.apk` (ou *Build > Generate Signed Bundle / APK* dans Android Studio).
3. **Build via GitHub** : dans *Settings > Secrets and variables > Actions*, créer `KEYSTORE_BASE64` (contenu du `.jks.b64`), `KEYSTORE_PASSWORD`, `KEY_ALIAS` (`tepg`) et `KEY_PASSWORD`.
   Puis *Actions > Build APK > Run workflow* : l'artefact **TEPG-release** contient l'APK signé et l'AAB (pour le Play Store).
4. **Mises à jour** : augmenter `versionCode` (+1) et `versionName` dans `app/build.gradle` avant chaque nouvelle version.

Sans identifiants de signature, `assembleRelease` produit un APK non signé (non installable).
