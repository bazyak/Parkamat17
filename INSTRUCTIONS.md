# Patch Instructions — Simple Unit Converter

Copy all files from this archive into your Steganofy-master project root,
replacing existing files where prompted. The directory structure here mirrors
the project exactly.

## What changed

### 1. Koin bug (already fixed in your zip via AndroidScopeComponent)
No further changes needed — your current code is correct Koin 3.x.

### 2. File picker always opens Pictures folder
`app/src/main/java/com/braffdev/steganofy/ui/common/file/picker/FilePickerViewModel.kt`
- Uses `DocumentsContract.EXTRA_INITIAL_URI` + `MediaStore.Images.Media.EXTERNAL_CONTENT_URI`
  to force the system picker to open at the device's Pictures/DCIM folder
  every time, instead of remembering the last location.
- The API check (`Build.VERSION.SDK_INT >= O`) keeps it safe for API 24.

### 3. New main screen — Unit Converter
NEW files:
- `app/src/main/java/com/braffdev/steganofy/ui/converter/ConversionEngine.kt`
  All conversion math — adapted directly from UnitConverterUltimate (Conversions.java
  + ConversionPresenter.java). No RxJava, no external dependencies.
- `app/src/main/java/com/braffdev/steganofy/ui/converter/UnitConverterActivity.kt`
  New launcher Activity. Category spinner + from/to unit spinners + input/result fields.
  Has a "Steganography" menu item that opens the old MainActivity.
- `app/src/main/res/layout/activity_unit_converter.xml`
- `app/src/main/res/menu/menu_converter.xml`

MODIFIED:
- `AndroidManifest.xml` — UnitConverterActivity is now the launcher.
  All other activities have `android:exported="false"` (fixes API 31+ requirement).

### 4. Renamed to "Simple Unit Converter"
- `app/src/main/res/values/strings.xml` — `app_name` and `about_title` updated.
  All converter UI strings added.

## Menu directory
If your project doesn't have a `res/menu/` folder yet, create it:
`app/src/main/res/menu/`
Then copy `menu_converter.xml` into it.
