package com.androidtecknowlogy.directory.interfaces;

/**
 * Created by nezspencer on 12/10/16.
 */

public interface ProfilePictureListener {

    void onPictureUploadSuccessful(String pictureDownloadUrl);

    void onPictureUploadFailed();
}
