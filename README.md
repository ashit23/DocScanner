ScanLibrary
ScanLibrary is an android document scanning library built on top of OpenCV, using the app you will be able to select the exact edges and crop the document accordingly from the selected 4 edges and change the perspective transformation of the cropped image.

Screenshots
Scan Input Scan Points After Scan Scanned Result
Videos
Scan Video
Using it in your project
If you are using android studio, add the dependency to your main app build.gradle this way:
compile project(':scanlibrary')
In your activity or fragment when you want to give an option of document scanning to user then: Start the scanlibrary ScanActivity, with this the app will go to library, below is the sample code snippet: Note: preference can be one of OPEN_CAMERA or OPEN_MEDIA or left empty, based on the passed preference the scan library decides to open camera or media or open the scan home page.
       int REQUEST_CODE = 99;
       int preference = ScanConstants.OPEN_CAMERA;
       Intent intent = new Intent(this, ScanActivity.class);
       intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
       startActivityForResult(intent, REQUEST_CODE);
Once the scanning is done, the application is returned from scan library to main app, to retrieve the scanned image, add onActivityResult in your activity or fragment from where you have started startActivityForResult, below is the sample code snippet:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                scannedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
IMPORTANT: This project uses the OPENCV Framework. Download the newest version here 'http://opencv.org/.
