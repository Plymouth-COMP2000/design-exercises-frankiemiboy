package com.example.restaurantmanager_app.user_management;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

// Handles user logic (CRUD Operations)


public class UserService {

    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework";

    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    // GET: /read_all_users/{student_id}
    public static void getAllUsers(
            Context context,
            String studentId,
            UsersResponseCallback callback
    ) {
        initQueue(context);
        String url = BASE_URL + "/read_all_users/" + studentId;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONArray usersArray = response.getJSONArray("users");
                        Type listType = new TypeToken<List<User>>() {}.getType();
                        List<User> users = gson.fromJson(usersArray.toString(), listType);
                        callback.onSuccess(users);
                    } catch (Exception e) {
                        Log.e("UserService", "Parsing error", e);
                        callback.onError("Parsing error");
                    }
                },
                error -> {
                    Log.e("UserService", "Volley error: ", error);
                    callback.onError("API request failed");
                }
        );
        requestQueue.add(request);
    }

    // GET: /read_user/{student_id}/{username}
    public static void getSingleUser(
            Context context,
            String studentId,
            String username,
            UserResponseCallback callback
    ) {
        initQueue(context);
        String url = BASE_URL + "/read_user/" + studentId + "/" + username;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        JSONObject userObject = response.getJSONObject("user");
                        User user = gson.fromJson(userObject.toString(), User.class);
                        callback.onSuccess(user);
                    } catch (Exception e) {
                        Log.e("UserService", "Parsing error", e);
                        callback.onError("Parsing error");
                    }
                },
                error -> {
                    Log.e("UserService", "Volley error: ", error);
                    callback.onError("API request failed");
                }
        );
        requestQueue.add(request);
    }

    // POST: /create_user/{student_id}
    public static void createUser(
            Context context,
            String studentId,
            User user,
            UserResponseCallback callback
    ) {
        initQueue(context);
        String url = BASE_URL + "/create_user/" + studentId;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", user.getUsername());
            requestBody.put("password", user.getPassword());
            requestBody.put("firstname", user.getFirstName());
            requestBody.put("lastname", user.getLastName());
            requestBody.put("email", user.getEmail());
            requestBody.put("contact", user.getContact());
            requestBody.put("usertype", user.getUsertype());
        } catch (Exception e) {
            Log.e("UserService", "JSON creation error", e);
            callback.onError("JSON creation error");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                response -> {
                    try {
                        callback.onSuccess(null);
                    } catch (Exception e) {
                        Log.e("UserService", "Parsing error", e);
                        callback.onError("Parsing error");
                    }
                },
                error -> {
                    Log.e("UserService", "Volley error: ", error);
                    callback.onError("API request failed");
                }
        );

        requestQueue.add(request);
    }


    // PUT: /update_user/{student_id}/{username}
    public static void updateUser(
            Context context,
            String studentId,
            String username,
            User user,
            UserResponseCallback callback
    ) {
        initQueue(context);
        String url = BASE_URL + "/update_user/" + studentId + "/" + username;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", user.getUsername());
            requestBody.put("password", user.getPassword());
            requestBody.put("firstname", user.getFirstName());
            requestBody.put("lastname", user.getLastName());
            requestBody.put("email", user.getEmail());
            requestBody.put("contact", user.getContact());
            requestBody.put("usertype", user.getUsertype());
        } catch (Exception e) {
            Log.e("UserService", "JSON creation error", e);
            callback.onError("JSON creation error");
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                requestBody,
                response -> {
                    try {
                        callback.onSuccess(null);
                    } catch (Exception e) {
                        Log.e("UserService", "Parsing error", e);
                        callback.onError("Parsing error");
                    }
                },
                error -> {
                    Log.e("UserService", "Volley error: ", error);
                    callback.onError("API request failed");
                }
        );

        requestQueue.add(request);
    }


    // DELETE: /delete_user/{student_id}/{username}
    public static void deleteUser(
            Context context,
            String studentId,
            String username,
            UserResponseCallback callback
    ) {
        initQueue(context);
        String url = BASE_URL + "/delete_user/" + studentId + "/" + username;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                response -> {
                    try {
                        callback.onSuccess(null);
                    } catch (Exception e) {
                        Log.e("UserService", "Parsing error", e);
                        callback.onError("Parsing error");
                    }
                },
                error -> {
                    Log.e("UserService", "Volley error: ", error);
                    callback.onError("API request failed");
                }
        );

        requestQueue.add(request);
    }


    // Callback interface for list of Users
    public interface UsersResponseCallback {
        void onSuccess(List<User> users);
        void onError(String message);
    }

    // Callback interface for single User
    public interface UserResponseCallback {
        void onSuccess(User user);
        void onError(String message);
    }
}
