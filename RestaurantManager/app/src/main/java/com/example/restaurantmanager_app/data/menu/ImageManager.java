package com.example.restaurantmanager_app.data.menu;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageManager {

    // Create menu image directory (once)
    // Otherwise, return current directory storing images
    private File getMenuImageDirectory(Context context) {
        File dir = new File(context.getFilesDir(), "menu_images");

        // Check if the directory already exists
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public String saveMenuImage(Context context, Bitmap bitmap) throws IOException {

        File dir = getMenuImageDirectory(context);

        String fileName = "menu_image_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(dir, fileName);

        FileOutputStream fos = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.close();

        return "menu_images/" + fileName; // Return the relative path of the saved image
    }

    public void deleteMenuImage(Context context, String imageName) {
        if (imageName == null) {
            return;
        }
        new File (context.getFilesDir(), imageName).delete();
    }
}
