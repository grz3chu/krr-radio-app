# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

KRR releases below are numbered by their Google Play `versionCode`; older entries
are the RadioDroid versions KRR is based on.

## [Unreleased]
### Changed
- Networking library updated from OkHttp 3.12 to 5.4
- AndroidX and Material Components libraries updated to current stable versions
  (Material Components was previously a beta release)

### Removed
- Unused SafetyNet dependency and compatibility code for Android versions older
  than 6.0 (TLS 1.2 workaround, legacy multidex)

### Fixed
- Country flags missing in release builds

## [50] - 2026-11-05
### Changed
- Release builds are optimized and shrunk with R8 (app size down by ~60%)

## [49] - 2026-11-05
### Added
- Android 16 (API 36) support, as required by Google Play
- Notification permission request (Android 13+)
- Prompt to allow "Alarms & reminders" so the radio alarm starts on time (Android 12+);
  without it the alarm falls back to an inexact one instead of failing
- "Nearby devices" permission request when enabling resume on Bluetooth connection (Android 12+)

### Changed
- Playback engine updated from ExoPlayer 2.13 to AndroidX Media3 1.11
- Edge-to-edge layout on all Android versions
- Recordings use the file extension of the stream codec (`.aac`, `.ogg`, `.flac`, `.mp3`)
- Minimum supported version raised to Android 6.0 (previously Android 4.1)
- Build updated to Gradle 9.8 and Android Gradle Plugin 9.5

### Fixed
- Crash when starting playback on Android 14+ (missing foreground service type)
- Radio alarm could not be set on Android 12+ without the exact alarm permission
- Saving and loading favourites and recording did nothing on Android 13+
- Play time, data usage and recording size in the full player stopped updating
- Bottom navigation hidden behind the system navigation bar
- Crash when opening the generic item list dialog (outdated bottom sheet class)
- End of stream corrupting ICY metadata handling and recordings

## [0.84] - 2020-12-28
### Added
- Refreshable favorites and history lists
- Mark removed stations red, and broken stations yellow
- Translation updates
- Adaptive launcher icon
- Testing framework
- Stop button to MPD
- Very basic android TV support
- LastFM Api key changeable by user in settings menu

### Fixed
- Recording in android 10
- Correctly display audio players in list of external play
- Play audio warnings as music and not as alarm
- False negatives in hls stream detection

## [0.83] - 2020-04-15
### Changed
- "Remove from favorites" usability
- Track history with icons disabled (#774)

### Fixed
- Added fallback if dns resolve does not return anything
- Fix state updating of record button (#785)
- Show previously picked time when editing alarm's time (#784)
- Start recording after storage permissions are granted (#783)

## [0.82] - 2020-03-07
### Fixed
- Audio focus on pause
- Sudden stop of playback after it beeing resumed after connection loss

### Changed
- Swap station name and track name in full screen player

## [0.81] - 2020-03-03
### Added
- Export history to m3u

### Fixed
- Make sure all.api.radio-browser.info is not used directly
- Play time in fullscreen player
- Some crashes
- Stop notification relaunch after stop
- External player interactions
- Autostart of notification

### Changed
- Library: material 1.2.0-alpha05
- Library: gson 2.8.6
- Library: cast 18.1.0
- Library: lifecycle 2.2.0
- Library: searchpreference 2.0.0

## [0.80] - 2020-02-10
### Added
- Fullscreen player
- Password support for MPD
- Show warning for use of metered connections
- Flag symbols in countries tab
- History of the played tracks
- Stations search now shows results as you type
- Option to resume on wired or bluetooth device reconnection

### Fixed
- Connection issues with android 4 for most people

### Changed
- Library: OKhttp 3.12.8
- Library: Cast 18.0.0
- Use countrycode field from API instead of country field
- Reworked user interface for MPD which now allows explicit management of several servers
- Improved user interface of recordings

### Removed
- Server selection from settings. There is an automatic fallback now.
- Old main server is not used anymore (www.radio-browser.info/webservice)

