# Rick and Morty App

En Android-app bygget i **Kotlin** med **Jetpack Compose** og **MVVM-arkitektur**, som viser karakterer fra [Rick and Morty API](https://rickandmortyapi.com/).  
Appen lar brukeren se karakterer fra API-et, lagre favoritter i lokal database og opprette egne karakterer.

App-struktur 
.
├── data/             # Database og API-repository
├── ui/               # Compose-skjermene
├── viewmodel/        # ViewModels for state-håndtering
├── model/            # Data-klasser (API + database)
└── MainActivity.kt   # Startpunkt for appen


##  Funksjonalitet
- Vise en liste over Rick and Morty-karakterer fra API-et
- Lagre og vise egne favorittkarakterer fra lokal database (Room)
- Opprette og lagre egendefinerte karakterer
- Navigasjon mellom flere skjermer via `AppNavigation`
- Moderne UI med **Jetpack Compose**

##  Teknologi
- **Kotlin**
- **Jetpack Compose**
- **MVVM (Model-View-ViewModel)**
- **Room Database**
- **Retrofit** (for API-kall)
- **Coroutines + Flow** (for asynkron databehandling)

##  Komme i gang

### Krav
- Android Studio (anbefalt Chipmunk eller nyere)
- Android SDK 35 eller høyere

### Installasjon
1. Klon repoet


##  Dokumentasjon
En mer detaljert forklaring av konseptet, funksjoner og koden finnes i prosjektmappen som en PDF-fil:

- `Rapport PGR208 Eksamen Høst 2024.pdf`

PDF-en beskriver:
- Konsept og beskrivelse
- Funksjonalitet i appen
- Kodedokumentasjon og gjennomgang med skjermbilder av Android emulator
