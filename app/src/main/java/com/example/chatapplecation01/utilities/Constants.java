package com.example.chatapplecation01.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_Collection_USERS = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_USER_TYPE = "userType"; // (1 = normal user)(2 = SRT user)
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECEIVER_ID ="receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final String KEY_COLLECTION_PRODUCT = "products";
    public static final String KEY_OWNER_ID = "ownerId";
    public static final String KEY_PRODUCT_ID = "productId";
    public static final String KEY_PRODUCT_NAME = "productName";
    public static final String KEY_PRODUCT_IMAGE = "productImage";
    public static final String KEY_PRODUCT_CATEGORY = "productCategory";
    public static final String KEY_LAST_SERVICE_DATE = "lastServiceDate";
    public static final String KEY_NEXT_SERVICE_DATE = "nextServiceDate";
    public static final String KEY_WARRANTY_EXPIRY_DATE = "warrantyExpiryDate";
    public static final String KEY_PRODUCT_NOTES = "notes";
    public static final String KEY_REMIND_DATE = "remindDate";
    public static final String KEY_PRODUCT_TYPE = "productType";
    public static final String KEY_EXPIRED_DATE = "expiredDate";


    public static HashMap<String, String> remoteMsgHeaders = null;
    public static HashMap<String, String> getRemoteMsgHeaders(){
        if (remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAAdmeGcQQ:APA91bFqXnz-YSjofISJXg2X9Uj7_ndiE0c-23ltgMpEmbTOrgcjZgSN3lWotDUESJZkxslmbc-xkGeKxJ4QHLEdN5oVjCgJQaCx_Tz9pTVkan3ihqiEJIl-UFLGQtFMV1bIoE8ZHymt"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );
        }
        return remoteMsgHeaders;
    }
}

