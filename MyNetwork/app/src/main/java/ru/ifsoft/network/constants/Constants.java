package ru.ifsoft.network.constants;

public interface Constants {

    // Attention! You can only change the values of the following constants:

    // YOUTUBE_API_KEY, EMOJI_KEYBOARD, WEB_SITE_AVAILABLE, GOOGLE_PAY_TEST_BUTTON, MY_AD_AFTER_ITEM_NUMBER,
    // APP_TEMP_FOLDER, VIDEO_FILE_MAX_SIZE, BILLING_KEY, WEB_SITE, CLIENT_ID, API_DOMAIN,
    // GHOST_MODE_COST, VERIFIED_BADGE_COST, DISABLE_ADS_COST
    // POST_CHARACTERS_LIMIT, HASHTAGS_COLOR

    // It is forbidden to change the value of constants, which are not indicated above !!!

    public static final Boolean FACEBOOK_AUTHORIZATION = true; // Allow login, signup with Facebook and "Services" section in Settings
    public static final Boolean UPGRADES_FEATURE = true; // Allow marketplace upgrades feature
    public static final Boolean MARKETPLACE_FEATURE = true; // Allow marketplace feature

    public static final Boolean EMOJI_KEYBOARD = true; // false = Do not display your own Emoji keyboard | true = allow display your own Emoji keyboard

    public static final Boolean WEB_SITE_AVAILABLE = true; // false = Do not show menu items (Open in browser, Copy profile link) in profile page | true = show menu items (Open in browser, Copy profile link) in profile page

    public static final Boolean GOOGLE_PAY_TEST_BUTTON = true; // false = Do not show google pay test button in section upgrades

    public static final int MY_AD_AFTER_ITEM_NUMBER = 0;  //After first item

    public static final String APP_TEMP_FOLDER = "network"; //directory for temporary storage of images from the camera

    public static final int VIDEO_FILE_MAX_SIZE = 7340035; //Max size for video file in bytes | For example 7mb = 7*1024*1024

    // Your API KEY for playing youtube video in app | See here: http://ifsoft.co.uk/help/how_to_using_youtube_video_in_android_app/

    public static final String YOUTUBE_API_KEY = "AIzaaSyAz8TvzJtaYkvsNNR2sQz2T9atmQZOyc3otK";

    // Google Pay settings | Settings for In-App Purchasing for Android | See here: http://ifsoft.co.uk/help/how_to_add_google_in_app_purchase/

    public static final String BILLING_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIB";

    public static final String WEB_SITE = "http://yakshanota.com/";  //web site url address

    // Client ID For identify the application | Must be the same with CLIENT_ID from server config: db.inc.php

    public static final String CLIENT_ID = "9443";  // Correct example: 12567 | Incorrect example: 0987

    public static final String API_DOMAIN = "http://yakshanota.com/";  // url address to which the application sends requests || 10.0.2.2

    public static final String API_FILE_EXTENSION = "";     // Attention! Do not change the value for this constant!
    public static final String API_VERSION = "v2";          // Attention! Do not change the value for this constant!

    // Attention! Do not change values for next constants!

    public static final String METHOD_NOTIFICATIONS_CLEAR = API_DOMAIN + "api/" + API_VERSION + "/method/notifications.clear" + API_FILE_EXTENSION;
    public static final String METHOD_GUESTS_CLEAR = API_DOMAIN + "api/" + API_VERSION + "/method/guests.clear" + API_FILE_EXTENSION;

    public static final String METHOD_GROUP_SEARCH_PRELOAD = API_DOMAIN + "api/" + API_VERSION + "/method/group.searchPreload" + API_FILE_EXTENSION;

    public static final String METHOD_ACCOUNT_PRIVACY = API_DOMAIN + "api/" + API_VERSION + "/method/account.privacy" + API_FILE_EXTENSION;

    public static final String METHOD_FRIENDS_REQUEST = API_DOMAIN + "api/" + API_VERSION + "/method/friends.sendRequest" + API_FILE_EXTENSION;
    public static final String METHOD_FRIENDS_ACCEPT = API_DOMAIN + "api/" + API_VERSION + "/method/friends.acceptRequest" + API_FILE_EXTENSION;
    public static final String METHOD_FRIENDS_REJECT = API_DOMAIN + "api/" + API_VERSION + "/method/friends.rejectRequest" + API_FILE_EXTENSION;
    public static final String METHOD_FRIENDS_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/friends.remove" + API_FILE_EXTENSION;
    public static final String METHOD_FRIENDS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/friends.get" + API_FILE_EXTENSION;

    public static final String METHOD_ACCOUNT_GET_SETTINGS = API_DOMAIN + "api/" + API_VERSION + "/method/account.getSettings" + API_FILE_EXTENSION;
    public static final String METHOD_DIALOGS_NEW_GET = API_DOMAIN + "api/" + API_VERSION + "/method/dialogs_new.get" + API_FILE_EXTENSION;
    public static final String METHOD_CHAT_UPDATE = API_DOMAIN + "api/" + API_VERSION + "/method/chat.update" + API_FILE_EXTENSION;

    public static final String METHOD_ACCOUNT_LOGIN = API_DOMAIN + "api/" + API_VERSION + "/method/account.signIn" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SIGNUP = API_DOMAIN + "api/" + API_VERSION + "/method/account.signUp" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_AUTHORIZE = API_DOMAIN + "api/" + API_VERSION + "/method/account.authorize" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SET_GCM_TOKEN = API_DOMAIN + "api/" + API_VERSION + "/method/account.setGcmToken" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_LOGINBYFACEBOOK = API_DOMAIN + "api/" + API_VERSION + "/method/account.signInByFacebook" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_RECOVERY = API_DOMAIN + "api/" + API_VERSION + "/method/account.recovery" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SETPASSWORD = API_DOMAIN + "api/" + API_VERSION + "/method/account.setPassword" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_DEACTIVATE = API_DOMAIN + "api/" + API_VERSION + "/method/account.deactivate" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SAVE_SETTINGS = API_DOMAIN + "api/" + API_VERSION + "/method/account.saveSettings" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_CONNECT_TO_FACEBOOK = API_DOMAIN + "api/" + API_VERSION + "/method/account.connectToFacebook" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_DISCONNECT_FROM_FACEBOOK = API_DOMAIN + "api/" + API_VERSION + "/method/account.disconnectFromFacebook" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_LOGOUT = API_DOMAIN + "api/" + API_VERSION + "/method/account.logOut" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SET_ALLOW_COMMENTS = API_DOMAIN + "api/" + API_VERSION + "/method/account.setAllowComments" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SET_ALLOW_MESSAGES = API_DOMAIN + "api/" + API_VERSION + "/method/account.setAllowMessages" + API_FILE_EXTENSION;

    public static final String METHOD_GIFTS_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/gifts.remove" + API_FILE_EXTENSION;
    public static final String METHOD_GIFTS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/gifts.get" + API_FILE_EXTENSION;
    public static final String METHOD_GIFTS_SELECT = API_DOMAIN + "api/" + API_VERSION + "/method/gifts.select" + API_FILE_EXTENSION;
    public static final String METHOD_GIFTS_SEND = API_DOMAIN + "api/" + API_VERSION + "/method/gifts.send" + API_FILE_EXTENSION;

    public static final String METHOD_ACCOUNT_SET_GEO_LOCATION = API_DOMAIN + "api/" + API_VERSION + "/method/account.setGeoLocation" + API_FILE_EXTENSION;

    public static final String METHOD_PROFILE_PEOPLE_NEARBY_GET = API_DOMAIN + "api/" + API_VERSION + "/method/profile.getPeopleNearby" + API_FILE_EXTENSION;

    public static final String METHOD_GUESTS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/guests.get" + API_FILE_EXTENSION;

    public static final String METHOD_SUPPORT_SEND_TICKET = API_DOMAIN + "api/" + API_VERSION + "/method/support.sendTicket" + API_FILE_EXTENSION;

    public static final String METHOD_PROFILE_GET = API_DOMAIN + "api/" + API_VERSION + "/method/profile.get" + API_FILE_EXTENSION;
    public static final String METHOD_PROFILE_FOLLOWINGS = API_DOMAIN + "api/" + API_VERSION + "/method/profile.followings" + API_FILE_EXTENSION;
    public static final String METHOD_PROFILE_FOLLOWERS = API_DOMAIN + "api/" + API_VERSION + "/method/profile.followers" + API_FILE_EXTENSION;
    public static final String METHOD_PROFILE_FOLLOW = API_DOMAIN + "api/" + API_VERSION + "/method/profile.follow" + API_FILE_EXTENSION;
    public static final String METHOD_WALL_GET = API_DOMAIN + "api/" + API_VERSION + "/method/wall.get" + API_FILE_EXTENSION;

    public static final String METHOD_GROUP_CREATE = API_DOMAIN + "api/" + API_VERSION + "/method/group.create" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_SAVE_SETTINGS = API_DOMAIN + "api/" + API_VERSION + "/method/group.saveSettings" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_GET = API_DOMAIN + "api/" + API_VERSION + "/method/group.get" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_GET_MY_GROUPS = API_DOMAIN + "api/" + API_VERSION + "/method/group.getMyGroups" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_GET_MANAGED_GROUPS = API_DOMAIN + "api/" + API_VERSION + "/method/group.getManagedGroups" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_GET_FOLLOWERS = API_DOMAIN + "api/" + API_VERSION + "/method/group.getFollowers" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_FOLLOW = API_DOMAIN + "api/" + API_VERSION + "/method/group.follow" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_SEARCH = API_DOMAIN + "api/" + API_VERSION + "/method/group.search" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_UPLOADPHOTO = API_DOMAIN + "api/" + API_VERSION + "/method/group.uploadPhoto" + API_FILE_EXTENSION;
    public static final String METHOD_GROUP_GET_WALL = API_DOMAIN + "api/" + API_VERSION + "/method/group.getWall" + API_FILE_EXTENSION;

    public static final String METHOD_BLACKLIST_GET = API_DOMAIN + "api/" + API_VERSION + "/method/blacklist.get" + API_FILE_EXTENSION;
    public static final String METHOD_BLACKLIST_ADD = API_DOMAIN + "api/" + API_VERSION + "/method/blacklist.add" + API_FILE_EXTENSION;
    public static final String METHOD_BLACKLIST_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/blacklist.remove" + API_FILE_EXTENSION;

    public static final String METHOD_NOTIFICATIONS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/notifications.get" + API_FILE_EXTENSION;
    public static final String METHOD_HASHTAGS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/hashtags.get" + API_FILE_EXTENSION;
    public static final String METHOD_FEEDS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/feeds.get" + API_FILE_EXTENSION;
    public static final String METHOD_ITEM_GET = API_DOMAIN + "api/" + API_VERSION + "/method/item.get" + API_FILE_EXTENSION;
    public static final String METHOD_STREAM_GET = API_DOMAIN + "api/" + API_VERSION + "/method/stream.get" + API_FILE_EXTENSION;
    public static final String METHOD_POPULAR_GET = API_DOMAIN + "api/" + API_VERSION + "/method/popular.get" + API_FILE_EXTENSION;

    public static final String METHOD_APP_CHECKUSERNAME = API_DOMAIN + "api/" + API_VERSION + "/method/app.checkUsername" + API_FILE_EXTENSION;
    public static final String METHOD_APP_TERMS = API_DOMAIN + "api/" + API_VERSION + "/method/app.terms" + API_FILE_EXTENSION;
    public static final String METHOD_APP_THANKS = API_DOMAIN + "api/" + API_VERSION + "/method/app.thanks" + API_FILE_EXTENSION;
    public static final String METHOD_APP_SEARCH = API_DOMAIN + "api/" + API_VERSION + "/method/app.search" + API_FILE_EXTENSION;

    public static final String METHOD_APP_SEARCH_PRELOAD = API_DOMAIN + "api/" + API_VERSION + "/method/app.searchPreload" + API_FILE_EXTENSION;

    public static final String METHOD_ITEMS_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/items.remove" + API_FILE_EXTENSION;
    public static final String METHOD_ITEMS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/items.get" + API_FILE_EXTENSION;
    public static final String METHOD_ITEMS_UPLOAD_IMG = API_DOMAIN + "api/" + API_VERSION + "/method/items.uploadImg" + API_FILE_EXTENSION;
    public static final String METHOD_ITEMS_NEW = API_DOMAIN + "api/" + API_VERSION + "/method/items.new" + API_FILE_EXTENSION;
    public static final String METHOD_ITEMS_EDIT = API_DOMAIN + "api/" + API_VERSION + "/method/items.edit" + API_FILE_EXTENSION;

    public static final String METHOD_FAVORITES_GET = API_DOMAIN + "api/" + API_VERSION + "/method/favorites.get" + API_FILE_EXTENSION;

    public static final String METHOD_CHAT_GET = API_DOMAIN + "api/" + API_VERSION + "/method/chat.get" + API_FILE_EXTENSION;
    public static final String METHOD_CHAT_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/chat.remove" + API_FILE_EXTENSION;
    public static final String METHOD_CHAT_GET_PREVIOUS = API_DOMAIN + "api/" + API_VERSION + "/method/chat.getPrevious" + API_FILE_EXTENSION;

    public static final String METHOD_MSG_NEW = API_DOMAIN + "api/" + API_VERSION + "/method/msg.new" + API_FILE_EXTENSION;
    public static final String METHOD_MSG_UPLOAD_IMG = API_DOMAIN + "api/" + API_VERSION + "/method/msg.uploadImg" + API_FILE_EXTENSION;

    public static final String METHOD_REFERRALS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/referrals.get" + API_FILE_EXTENSION;

    public static final String METHOD_MARKET_SEARCH = API_DOMAIN + "api/" + API_VERSION + "/method/market.search" + API_FILE_EXTENSION;
    public static final String METHOD_MARKET_SEARCH_PRELOAD = API_DOMAIN + "api/" + API_VERSION + "/method/market.preload" + API_FILE_EXTENSION;
    public static final String METHOD_MARKET_NEW_ITEM = API_DOMAIN + "api/" + API_VERSION + "/method/market.newItem" + API_FILE_EXTENSION;
    public static final String METHOD_MARKET_REMOVE_ITEM = API_DOMAIN + "api/" + API_VERSION + "/method/market.removeItem" + API_FILE_EXTENSION;
    public static final String METHOD_MARKET_UPLOAD_IMG = API_DOMAIN + "api/" + API_VERSION + "/method/market.uploadImg" + API_FILE_EXTENSION;
    public static final String METHOD_MARKET_GET_MY_ITEMS = API_DOMAIN + "api/" + API_VERSION + "/method/market.getMyItems" + API_FILE_EXTENSION;

    // added for version 3.9

    public static final String METHOD_GET_STICKERS = API_DOMAIN + "api/" + API_VERSION + "/method/stickers.get" + API_FILE_EXTENSION;

    // added for version 4.1

    public static final String METHOD_CHAT_NOTIFY = API_DOMAIN + "api/" + API_VERSION + "/method/chat.notify" + API_FILE_EXTENSION;

    // added for version 4.3

    public static final String METHOD_ACCOUNT_SET_PRO_MODE = API_DOMAIN + "api/" + API_VERSION + "/method/account.setProMode" + API_FILE_EXTENSION;

    // added for version 4.6

    public static final String METHOD_FEELINGS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/feelings.get" + API_FILE_EXTENSION;
    public static final String METHOD_ACCOUNT_SET_FEELING = API_DOMAIN + "api/" + API_VERSION + "/method/account.setMood" + API_FILE_EXTENSION;

    // added for version 4.9

    public static final String METHOD_APP_CHECK_EMAIL = API_DOMAIN + "api/" + API_VERSION + "/method/app.checkEmail" + API_FILE_EXTENSION;
    public static final String METHOD_ITEM_GET_IMAGES = API_DOMAIN + "api/" + API_VERSION + "/method/item.getImages" + API_FILE_EXTENSION;
    public static final String METHOD_ITEM_GET_COMMENTS = API_DOMAIN + "api/" + API_VERSION + "/method/item.getComments" + API_FILE_EXTENSION;
    public static final String METHOD_PROFILE_UPLOAD_IMAGE = API_DOMAIN + "api/" + API_VERSION + "/method/profile.uploadImg" + API_FILE_EXTENSION;

    // added for version 5.0

    public static final String METHOD_ACCOUNT_SET_ALLOW_GALLERY_COMMENTS = API_DOMAIN + "api/" + API_VERSION + "/method/account.setAllowGalleryComments" + API_FILE_EXTENSION;

    public static final String METHOD_GALLERY_UPLOAD_IMAGE = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.uploadImg" + API_FILE_EXTENSION;
    public static final String METHOD_GALLERY_UPLOAD_VIDEO = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.uploadVideo" + API_FILE_EXTENSION;
    public static final String METHOD_GALLERY_ITEM_NEW = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.add" + API_FILE_EXTENSION;
    public static final String METHOD_GALLERY_ITEM_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.remove" + API_FILE_EXTENSION;
    public static final String METHOD_GALLERY_ITEM_INFO = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.info" + API_FILE_EXTENSION;
    public static final String METHOD_GALLERY_GET = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.get" + API_FILE_EXTENSION;

    public static final String METHOD_REPORT_NEW = API_DOMAIN + "api/" + API_VERSION + "/method/reports.new" + API_FILE_EXTENSION;

    public static final String METHOD_COMMENTS_REMOVE = API_DOMAIN + "api/" + API_VERSION + "/method/comments.remove" + API_FILE_EXTENSION;
    public static final String METHOD_COMMENTS_NEW = API_DOMAIN + "api/" + API_VERSION + "/method/comments.new" + API_FILE_EXTENSION;

    public static final String METHOD_LIKES_LIKE = API_DOMAIN + "api/" + API_VERSION + "/method/likes.like" + API_FILE_EXTENSION;
    public static final String METHOD_LIKES_GET = API_DOMAIN + "api/" + API_VERSION + "/method/likes.get" + API_FILE_EXTENSION;


    public static final String METHOD_VIDEO_UPLOAD = API_DOMAIN + "api/" + API_VERSION + "/method/gallery.uploadVideo" + API_FILE_EXTENSION;

    // added for version 5.1

    public static final String METHOD_ACCOUNT_UPGRADE = API_DOMAIN + "api/" + API_VERSION + "/method/account.upgrade" + API_FILE_EXTENSION;

    public static final String METHOD_PAYMENTS_NEW = API_DOMAIN + "api/" + API_VERSION + "/method/payments.new" + API_FILE_EXTENSION;
    public static final String METHOD_PAYMENTS_GET = API_DOMAIN + "api/" + API_VERSION + "/method/payments.get" + API_FILE_EXTENSION;

    public static final String METHOD_MELA_GET = API_DOMAIN + "api/" + API_VERSION + "/method/mela.get" + API_FILE_EXTENSION;
    public static final String METHOD_ACTIVE_ARTIST = API_DOMAIN + "api/" + API_VERSION + "/method/artistlist.get" + API_FILE_EXTENSION;
    public static final String METHOD_ACTIVE_ARTIST_SEARCH = API_DOMAIN + "api/" + API_VERSION + "/method/artistsearch.get" + API_FILE_EXTENSION;
    public static final String METHOD_VIDEO_LIST = API_DOMAIN + "api/" + API_VERSION + "/method/allvideolist.get" + API_FILE_EXTENSION;
    public static final String METHOD_VIDEO_SEARCH = API_DOMAIN + "api/" + API_VERSION + "/method/allvideolistsearch.get" + API_FILE_EXTENSION;

    public static final String METHOD_SIGNIN_OTP = API_DOMAIN + "api/" + API_VERSION + "/method/account.signInOtp" + API_FILE_EXTENSION;
    public static final String METHOD_SIGNIN_OTP_CHECK = API_DOMAIN + "api/" + API_VERSION + "/method/account.signInOtpCheck" + API_FILE_EXTENSION;
    public static final String METHOD_SIGNUP_OTP = API_DOMAIN + "api/" + API_VERSION + "/method/account.signUpOtp" + API_FILE_EXTENSION;
    public static final String METHOD_ADDS_LIST = API_DOMAIN + "api/" + API_VERSION + "/method/listAdds" + API_FILE_EXTENSION;
    public static final String METHOD_EVENTS_LIST = API_DOMAIN + "api/" + API_VERSION + "/method/listEvents" + API_FILE_EXTENSION;
    public static final String METHOD_EVENTS_MELA_LIST = API_DOMAIN + "api/" + API_VERSION + "/method/listMela" + API_FILE_EXTENSION;
    public static final String METHOD_EVENTS_CREATE = API_DOMAIN + "api/" + API_VERSION + "/method/save.events" + API_FILE_EXTENSION;


    public static final String METHOD_INACTIVE_ARTIST = API_DOMAIN + "m/parichaya/parichaya.html" ;
    public static final String METHOD_SUPPORT_ARTIST = API_DOMAIN + "m/help/help.html" ;
    public static final String METHOD_EVENT_CALENDAR = API_DOMAIN + "m/event/event.html" ;
    public static final String METHOD_ADVERTISE = API_DOMAIN + "m/adds/adds.html" ;

    // Other Constants

    public static final int APP_TYPE_ALL = -1;
    public static final int APP_TYPE_MANAGER = 0;
    public static final int APP_TYPE_WEB = 1;
    public static final int APP_TYPE_ANDROID = 2;
    public static final int APP_TYPE_IOS = 3;


    public static final int IMAGE_TYPE_PROFILE_PHOTO = 0;
    public static final int IMAGE_TYPE_PROFILE_COVER = 1;

    public static final int GALLERY_ITEM_TYPE_IMAGE = 0;
    public static final int GALLERY_ITEM_TYPE_VIDEO = 1;

    public static final int REPORT_TYPE_ITEM = 0;
    public static final int REPORT_TYPE_PROFILE = 1;
    public static final int REPORT_TYPE_MESSAGE = 2;
    public static final int REPORT_TYPE_COMMENT = 3;
    public static final int REPORT_TYPE_GALLERY_ITEM = 4;
    public static final int REPORT_TYPE_MARKET_ITEM = 5;
    public static final int REPORT_TYPE_COMMUNITY = 6;

    public static final int VOLLEY_REQUEST_SECONDS = 15; //SECONDS TO REQUEST

    public static final int POST_TYPE_DEFAULT = 0;
    public static final int POST_TYPE_PHOTO_UPDATE = 1;
    public static final int POST_TYPE_COVER_UPDATE = 2;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_PHOTO = 1;                  //WRITE_EXTERNAL_STORAGE
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_COVER = 2;                  //WRITE_EXTERNAL_STORAGE
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 3;                               //ACCESS_COARSE_LOCATION
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_VIDEO_IMAGE = 4;            //WRITE_EXTERNAL_STORAGE

    public static final int GHOST_MODE_COST = 100;      //Cost in Credits
    public static final int VERIFIED_BADGE_COST = 150;  //Cost in Credits
    public static final int PRO_MODE_COST = 170;        //Cost in Credits
    public static final int DISABLE_ADS_COST = 200;     //Cost in Credits

    public static final int LIST_ITEMS = 20;

    public static final int POST_CHARACTERS_LIMIT = 1000;

    public static final int ENABLED = 1;
    public static final int DISABLED = 0;

    public static final int GCM_ENABLED = 1;
    public static final int GCM_DISABLED = 0;

    public static final int ADMOB_ENABLED = 1;
    public static final int ADMOB_DISABLED = 0;

    public static final int COMMENTS_ENABLED = 1;
    public static final int COMMENTS_DISABLED = 0;

    public static final int MESSAGES_ENABLED = 1;
    public static final int MESSAGES_DISABLED = 0;

    public static final int ERROR_SUCCESS = 0;

    public static final int SEX_UNKNOWN = 0;
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;

    public static final int NOTIFY_TYPE_LIKE = 0;
    public static final int NOTIFY_TYPE_FOLLOWER = 1;
    public static final int NOTIFY_TYPE_MESSAGE = 2;
    public static final int NOTIFY_TYPE_COMMENT = 3;
    public static final int NOTIFY_TYPE_COMMENT_REPLY = 4;
    public static final int NOTIFY_TYPE_FRIEND_REQUEST_ACCEPTED = 5;
    public static final int NOTIFY_TYPE_GIFT = 6;

    public static final int NOTIFY_TYPE_IMAGE_COMMENT = 7;
    public static final int NOTIFY_TYPE_IMAGE_COMMENT_REPLY = 8;
    public static final int NOTIFY_TYPE_IMAGE_LIKE = 9;

    public static final int NOTIFY_TYPE_VIDEO_COMMENT = 10;
    public static final int NOTIFY_TYPE_VIDEO_COMMENT_REPLY = 11;
    public static final int NOTIFY_TYPE_VIDEO_LIKE = 12;

    public static final int NOTIFY_TYPE_PROFILE_PHOTO_APPROVE = 2003;
    public static final int NOTIFY_TYPE_PROFILE_PHOTO_REJECT = 2004;
    public static final int NOTIFY_TYPE_PROFILE_COVER_APPROVE = 2007;
    public static final int NOTIFY_TYPE_PROFILE_COVER_REJECT = 2008;

    public static final int NOTIFY_TYPE_REFERRAL = 14;

    public static final int GCM_NOTIFY_CONFIG = 0;
    public static final int GCM_NOTIFY_SYSTEM = 1;
    public static final int GCM_NOTIFY_CUSTOM = 2;
    public static final int GCM_NOTIFY_LIKE = 3;
    public static final int GCM_NOTIFY_ANSWER = 4;
    public static final int GCM_NOTIFY_QUESTION = 5;
    public static final int GCM_NOTIFY_COMMENT = 6;
    public static final int GCM_NOTIFY_FOLLOWER = 7;
    public static final int GCM_NOTIFY_PERSONAL = 8;
    public static final int GCM_NOTIFY_MESSAGE = 9;
    public static final int GCM_NOTIFY_COMMENT_REPLY = 10;
    public static final int GCM_FRIEND_REQUEST_INBOX = 11;
    public static final int GCM_FRIEND_REQUEST_ACCEPTED = 12;
    public static final int GCM_NOTIFY_GIFT = 14;
    public static final int GCM_NOTIFY_SEEN = 15;
    public static final int GCM_NOTIFY_TYPING = 16;
    public static final int GCM_NOTIFY_URL = 17;

    public static final int GCM_NOTIFY_IMAGE_COMMENT_REPLY = 18;
    public static final int GCM_NOTIFY_IMAGE_COMMENT = 19;
    public static final int GCM_NOTIFY_IMAGE_LIKE = 20;

    public static final int GCM_NOTIFY_VIDEO_COMMENT_REPLY = 21;
    public static final int GCM_NOTIFY_VIDEO_COMMENT = 22;
    public static final int GCM_NOTIFY_VIDEO_LIKE = 23;

    public static final int GCM_NOTIFY_REFERRAL = 24;

    public static final int GCM_NOTIFY_TYPING_START = 27;
    public static final int GCM_NOTIFY_TYPING_END = 28;

    public static final int GCM_NOTIFY_PROFILE_PHOTO_APPROVE = 1003;
    public static final int GCM_NOTIFY_PROFILE_PHOTO_REJECT = 1004;
    public static final int GCM_NOTIFY_PROFILE_COVER_APPROVE = 1007;
    public static final int GCM_NOTIFY_PROFILE_COVER_REJECT = 1008;


    public static final int ERROR_LOGIN_TAKEN = 300;
    public static final int ERROR_EMAIL_TAKEN = 301;
    public static final int ERROR_FACEBOOK_ID_TAKEN = 302;

    public static final int ACCOUNT_STATE_ENABLED = 0;
    public static final int ACCOUNT_STATE_DISABLED = 1;
    public static final int ACCOUNT_STATE_BLOCKED = 2;
    public static final int ACCOUNT_STATE_DEACTIVATED = 3;

    public static final int ACCOUNT_TYPE_USER = 0;
    public static final int ACCOUNT_TYPE_GROUP = 1;

    public static final int ERROR_UNKNOWN = 100;
    public static final int ERROR_ACCESS_TOKEN = 101;

    public static final int ERROR_ACCOUNT_ID = 400;

    public static final int UPLOAD_TYPE_PHOTO = 0;
    public static final int UPLOAD_TYPE_COVER = 1;

    public static final int ACTION_NEW = 1;
    public static final int ACTION_EDIT = 2;
    public static final int SELECT_POST_IMG = 3;
    public static final int VIEW_CHAT = 4;
    public static final int CREATE_POST_IMG = 5;
    public static final int SELECT_CHAT_IMG = 6;
    public static final int CREATE_CHAT_IMG = 7;
    public static final int FEED_NEW_POST = 8;
    public static final int FRIENDS_SEARCH = 9;
    public static final int ITEM_EDIT = 10;
    public static final int STREAM_NEW_POST = 11;
    public static final int ITEM_REPOST = 12;
    public static final int ITEM_ACTIONS_MENU = 14;
    public static final int ITEM_ACTION_REPOST = 15;

    public static final int ITEM_TYPE_IMAGE = 0;
    public static final int ITEM_TYPE_VIDEO = 1;
    public static final int ITEM_TYPE_POST = 2;
    public static final int ITEM_TYPE_COMMENT = 3;
    public static final int ITEM_TYPE_GALLERY = 4;

    public static final int PA_BUY_CREDITS = 0;
    public static final int PA_BUY_GIFT = 1;
    public static final int PA_BUY_VERIFIED_BADGE = 2;
    public static final int PA_BUY_GHOST_MODE = 3;
    public static final int PA_BUY_DISABLE_ADS = 4;
    public static final int PA_BUY_REGISTRATION_BONUS = 5;
    public static final int PA_BUY_REFERRAL_BONUS = 6;
    public static final int PA_BUY_MANUAL_BONUS = 7;
    public static final int PA_BUY_PRO_MODE = 8;
    public static final int PA_BUY_SPOTLIGHT = 9;
    public static final int PA_BUY_MESSAGE_PACKAGE = 10;

    public static final int PT_UNKNOWN = 0;
    public static final int PT_CREDITS = 1;
    public static final int PT_CARD = 2;
    public static final int PT_GOOGLE_PURCHASE = 3;
    public static final int PT_APPLE_PURCHASE = 4;
    public static final int PT_ADMOB_REWARDED_ADS = 5;
    public static final int PT_BONUS = 6;

    public static final String TAG = "TAG";

    public static final String HASHTAGS_COLOR = "#5BCFF2";

    public static final String TAG_UPDATE_BADGES = "update_badges";
}
