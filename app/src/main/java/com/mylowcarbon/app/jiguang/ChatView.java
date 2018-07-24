package com.mylowcarbon.app.jiguang;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mylowcarbon.app.R;
import com.mylowcarbon.app.model.ModelDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.CameraControllerListener;
import cn.jiguang.imui.chatinput.listener.OnCameraCallbackListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.record.RecordVoiceButton;
import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.BaseMessageViewHolder;
import cn.jiguang.imui.messages.CustomMsgConfig;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.messages.PhotoViewHolder;
import cn.jiguang.imui.messages.TxtViewHolder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.callback.ProgressUpdateCallback;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;


public class ChatView extends RelativeLayout implements View.OnTouchListener {
    private static final String TAG = "ChatView";

    private Activity mActivity;
    private Context mContext;
    //    private TextView mTitle;
    private MessageList mMsgList;
    private ChatInputView mChatInput;
    private RecordVoiceButton mRecordVoiceBtn;
    private NestedScrollView nestedScrollView;
    private boolean mHasInit;
    private int mHeight;
    private boolean mHasKeyboard;

    private MsgListAdapter<JMUIMessage> mAdapter;
    private List<JMUIMessage> mData = new ArrayList<>();
    private List<cn.jpush.im.android.api.model.Message> mMessageList = new ArrayList<>();
    private ChatView mChatView;
    public static final int REQUEST_RECORD_VOICE_PERMISSION = 0x0001;
    public static final int REQUEST_CAMERA_PERMISSION = 0x0002;
    public static final int REQUEST_PHOTO_PERMISSION = 0x0003;

    private InputMethodManager mImm;
    private Window mWindow;
    private Conversation mConv;
    private String mTargetId;
     private boolean mIsSingle = true;
    private Long mGroupId;
    private UserInfo mMyInfo;
    public static final int PAGE_MESSAGE_COUNT = 20;
    private int mOffset = PAGE_MESSAGE_COUNT;
    private int mStart;
    private ArrayList<String> mPathList = new ArrayList<>();
    private ArrayList<String> mMsgIdList = new ArrayList<>();
    private String curentUploadPhotoPath;
    public ChatView(Context context) {
        super(context);
        mContext = context;
    }

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    public void initModule() {
//        mTitle = (TextView) findViewById(R.id.title_tv);
        mChatView = this;
        mMsgList = (MessageList) findViewById(R.id.msg_list);
        mChatInput = (ChatInputView) findViewById(R.id.chat_input);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        mRecordVoiceBtn = mChatInput.getRecordVoiceButton();
        mMsgList.setNestedScrollingEnabled(false);
        mChatInput.setMenuContainerHeight(819);
        ImageButton mSwitchCameraBtn = (ImageButton) mChatInput.findViewById(cn.jiguang.imui.chatinput.R.id.aurora_ib_camera_record_video);
        if (mSwitchCameraBtn != null) {
            mSwitchCameraBtn.setVisibility(GONE);
        }


    }

//    public void setTitle(String title) {
//        mTitle.setText(title);
//    }

    public void setMenuClickListener(OnMenuClickListener listener) {
        mChatInput.setMenuClickListener(listener);
    }

    public void setAdapter(MsgListAdapter adapter) {
        mMsgList.setAdapter(adapter);
    }

    public void setRecordVoiceFile(String path, String fileName) {
        mRecordVoiceBtn.setVoiceFilePath(path, fileName);
    }

    public void setCameraCaptureFile(String path, String fileName) {
        mChatInput.setCameraCaptureFile(path, fileName);
    }

    public void setRecordVoiceListener(RecordVoiceListener listener) {
        mRecordVoiceBtn.setRecordVoiceListener(listener);
    }

    public void setOnCameraCallbackListener(OnCameraCallbackListener listener) {
        mChatInput.setOnCameraCallbackListener(listener);
    }

//    public void setKeyboardChangedListener(OnKeyboardChangedListener listener) {
//        mKeyboardListener = listener;
//    }
//
//    public void setOnSizeChangedListener(OnSizeChangedListener listener) {
//        mSizeChangedListener = listener;
//    }

    public void setOnTouchListener(OnTouchListener listener) {
        mMsgList.setOnTouchListener(listener);
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        if(mSizeChangedListener != null){
//            mSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
//        }
//    }
    public void setCameraControllerListener(CameraControllerListener listener) {
        mChatInput.setCameraControllerListener(listener);
    }

    public ChatInputView getChatInputView() {
        return mChatInput;
    }


    public void init(Activity activity, String targetId) {

        Log.e(TAG, "init targetId = " + targetId);
        mActivity = activity;
        if (TextUtils.isEmpty(targetId)) {
            return;
        }
        JMessageClient.registerEventReceiver(this);
        mMyInfo = JMessageClient.getMyInfo();
        try {
//            mTargetId = "18215560956";//   18680758192
            mTargetId = targetId;//   18680758192
            initModule();
            mConv = JMessageClient.getSingleConversation(mTargetId);

            if (mConv == null) {
                Log.i(TAG, "create new conversation");
                mConv = Conversation.createSingleConversation(mTargetId);
            }
            if (mConv == null) {
                Log.i(TAG, "create new conversation fail");

                return;
            }
            //进入会话防止通知
            JMessageClient.enterSingleConversation(targetId);

            this.mMessageList = mConv.getMessagesFromNewest(0, mOffset);
            mStart = mOffset;

            if (mMessageList.size() > 0) {
                for (cn.jpush.im.android.api.model.Message message : mMessageList) {
                    JMUIMessage jmuiMessage = new JMUIMessage(message);
                    Log.e(TAG, jmuiMessage.toString());
                    mData.add(jmuiMessage);
                }
            }

            this.mImm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            mWindow = mActivity.getWindow();
            mChatView.setOnTouchListener(this);
            initMsgAdapter();
            mChatView.setMenuClickListener(new OnMenuClickListener() {

                @Override
                public boolean onSendTextMessage(CharSequence input) {
                    if (input.length() == 0) {
                        return false;
                    }
                    TextContent textContent = new TextContent(input.toString());
                    cn.jpush.im.android.api.model.Message message = mConv.createSendMessage(textContent);
                    JMessageClient.sendMessage(message);
                    final JMUIMessage msg = new JMUIMessage(message);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addToStart(msg, true);
                            scrollToBottom();
                        }
                    });
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int status, String desc) {
                            mAdapter.updateMessage(msg);
                            if (status == 0) {
                                Log.i(TAG, "send message succeed!");
                            } else {
                                Log.i(TAG, "send message failed " + desc);
                            }
                        }
                    });
                    return true;
                }

                @Override
                public void onSendFiles(List<FileItem> list) {
                    dismissSoftInput();

                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    Log.e(TAG, "onSendFiles  list.size() : " + list.size());

                    for (final FileItem item : list) {
                        Log.e(TAG, "onSendFiles item   : " + item.getFilePath());

                        if (item.getType() == FileItem.Type.Image) {
                            Bitmap bitmap = BitmapLoader.getBitmapFromFile(item.getFilePath(), 720, 1280);
                            ImageContent.createImageContentAsync(bitmap, new ImageContent.CreateImageContentCallback() {
                                @Override
                                public void gotResult(int status, String desc, ImageContent imageContent) {
                                    Log.e(TAG, "onSendFiles item  gotResult status : " + status);

                                    if (status == 0) {
                                        cn.jpush.im.android.api.model.Message msg = mConv.createSendMessage(imageContent);
                                        JMessageClient.sendMessage(msg);
                                        final JMUIMessage jmuiMessage = new JMUIMessage(msg);
                                        jmuiMessage.setMediaFilePath(item.getFilePath());
                                        mActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.addToStart(jmuiMessage, true);
                                                scrollToBottom();
                                            }
                                        });
                                        msg.setOnContentUploadProgressCallback(new ProgressUpdateCallback() {
                                            @Override
                                            public void onProgressUpdate(double v) {
                                                jmuiMessage.setProgress(Math.ceil(v * 100) + "%");
                                                Log.w(TAG, "Uploading image progress" + Math.ceil(v * 100) + "%");
                                                mAdapter.updateMessage(jmuiMessage);
                                            }
                                        });
                                        msg.setOnSendCompleteCallback(new BasicCallback() {
                                            @Override
                                            public void gotResult(int status, String desc) {
                                                mAdapter.updateMessage(jmuiMessage);
                                                if (status == 0) {
                                                    Log.i(TAG, "Send image succeed");
                                                } else {
                                                    Log.i(TAG, "Send image failed, " + desc);
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                        } else if (item.getType() == FileItem.Type.Video) {
//                            message = new MyMessage(null, IMessage.MessageType.SEND_VIDEO.ordinal());
//                            message.setDuration(((VideoItem) item).getDuration());
                        } else {
                            throw new RuntimeException("Invalid FileItem type. Must be Type.Image or Type.Video.");
                        }
                    }
                }

                @Override
                public boolean switchToMicrophoneMode() {
                    scrollToBottom();
                    if ((ActivityCompat.checkSelfPermission(mActivity,
                            "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(mActivity,
                            "android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(mActivity,
                            "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{
                                "android.permission.RECORD_AUDIO",
                                "android.permission.WRITE_EXTERNAL_STORAGE",
                                "android.permission.READ_EXTERNAL_STORAGE"}, REQUEST_RECORD_VOICE_PERMISSION);
                        return false;
                    }
                    return true;
                }

                @Override
                public boolean switchToGalleryMode() {
                    scrollToBottom();
                    boolean bool = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;

                    if (bool) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        }, REQUEST_PHOTO_PERMISSION);
                        return false;
                    }
                    return true;
                }

                @Override
                public boolean switchToCameraMode() {
                    scrollToBottom();
                    boolean bool1 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
                    boolean bool2 = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
                    Log.e(TAG, "switchToCameraMode : " + bool1);
                    Log.e(TAG, "switchToCameraMode : " + bool2);
                    if (bool1 || bool2) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{
                                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, REQUEST_CAMERA_PERMISSION);

                        return false;
                    }
                    File rootDir = mActivity.getFilesDir();
                    String fileDir = rootDir.getAbsolutePath() + "/photo";
                    mChatView.setCameraCaptureFile(fileDir, "temp_photo");
                    return true;

                }

                @Override
                public boolean switchToEmojiMode() {
                    scrollToBottom();
                    return true;
                }

            });

            mChatView.setRecordVoiceListener(new RecordVoiceListener() {
                @Override
                public void onStartRecord() {
                    // Show record voice interface
                    // 设置存放录音文件目录
                    File rootDir = mActivity.getFilesDir();
                    String fileDir = rootDir.getAbsolutePath() + "/voice";
                    mChatView.setRecordVoiceFile(fileDir, new DateFormat().format("yyyy_MMdd_hhmmss",
                            Calendar.getInstance(Locale.CHINA)) + "");
                }

                @Override
                public void onFinishRecord(File voiceFile, int duration) {
                    try {
                        VoiceContent content = new VoiceContent(voiceFile, duration);
                        cn.jpush.im.android.api.model.Message msg = mConv.createSendMessage(content);
                        JMessageClient.sendMessage(msg);
                        final JMUIMessage jmuiMessage = new JMUIMessage(msg);
                        mAdapter.addToStart(jmuiMessage, true);
                        msg.setOnSendCompleteCallback(new BasicCallback() {
                            @Override
                            public void gotResult(int status, String s) {
                                mAdapter.updateMessage(jmuiMessage);
                                if (status == 0) {
                                    Log.i(TAG, "send voice message succeed!");
                                } else {
                                    Log.i(TAG, "send voice message failed");
                                }
                            }
                        });
                        dismissSoftInput();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelRecord() {

                }

                /**
                 * In preview record voice layout, fires when click cancel button
                 * Add since chatinput v0.7.3
                 */
                @Override
                public void onPreviewCancel() {

                }

                /**
                 * In preview record voice layout, fires when click send button
                 * Add since chatinput v0.7.3
                 */
                @Override
                public void onPreviewSend() {

                }
            });

            mChatView.setOnCameraCallbackListener(new OnCameraCallbackListener() {
                @Override
                public void onTakePictureCompleted(final String photoPath) {
                    Log.e(TAG, "onTakePictureCompleted  photoPath : " + photoPath);

                    if(!TextUtils.isEmpty(curentUploadPhotoPath)){
                        Log.e(TAG, "图片正在上传  photoPath : " + photoPath);

                        return;
                    }
                    curentUploadPhotoPath = photoPath;
                    dismissSoftInput();

//                    Bitmap bitmap = BitmapLoader.getBitmapFromFile(photoPath, 720, 1280);
                    ImageContent.createImageContentAsync(new File(photoPath), new ImageContent.CreateImageContentCallback() {
                        @Override
                        public void gotResult(int status, String desc, ImageContent imageContent) {
                            Log.e(TAG, "onTakePictureCompleted gotResult imageContent : " + imageContent.getImg_link());
                            curentUploadPhotoPath = "";
                            if (status == 0) {
                                cn.jpush.im.android.api.model.Message msg = mConv.createSendMessage(imageContent);
                                JMessageClient.sendMessage(msg);
                                Log.e(TAG, "onTakePictureCompleted gotResult msg : " + msg.getContent());

                                final JMUIMessage jmuiMessage = new JMUIMessage(msg);
                                jmuiMessage.setMediaFilePath(photoPath);
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.addToStart(jmuiMessage, true);
                                        scrollToBottom();
                                    }
                                });
                                msg.setOnContentUploadProgressCallback(new ProgressUpdateCallback() {
                                    @Override
                                    public void onProgressUpdate(double v) {
                                       jmuiMessage.setProgress(Math.ceil(v * 100) + "%");
                                        Log.w(TAG, "Uploading image progress" + Math.ceil(v * 100) + "%");
                                        mAdapter.updateMessage(jmuiMessage);
                                    }
                                });
                                msg.setOnSendCompleteCallback(new BasicCallback() {
                                    @Override
                                    public void gotResult(int status, String desc) {
                                        mAdapter.updateMessage(jmuiMessage);
                                        if (status == 0) {
                                            Log.i(TAG, "Send image succeed");
                                        } else {
                                            Log.i(TAG, "Send image failed, " + desc);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

                @Override
                public void onStartVideoRecord() {

                }

                @Override
                public void onFinishVideoRecord(String videoPath) {

                }

                @Override
                public void onCancelVideoRecord() {

                }
            });

            mChatView.getChatInputView().getInputView().setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    scrollToBottom();
                    return false;
                }
            });




        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "will start LoginActivity");
//            Intent intent = new Intent();
//            intent.setClass(mActivity, LoginActivity.class);
//            mActivity.startActivity(intent);
//            mActivity.finish();
        }
    }


    private void initMsgAdapter() {
        final float density = getResources().getDisplayMetrics().density;
        final float MIN_WIDTH = 60 * density;
        final float MAX_WIDTH = 200 * density;
        final float MIN_HEIGHT = 60 * density;
        final float MAX_HEIGHT = 200 * density;
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                // You can use other image load libraries.
                Log.e(TAG, "loadAvatarImage string : " + string);
                if (string.contains("R.drawable")) {
                    Integer resId = getResources().getIdentifier(string.replace("R.drawable.", ""),
                            "drawable", mContext.getPackageName());

                    avatarImageView.setImageResource(resId);
                } else {
                    Glide.with(mContext)
                            .load(string)
                            .apply(new RequestOptions().placeholder(R.drawable.aurora_headicon_default))
                            .into(avatarImageView);
                }
            }

            /**
             * Load image message
             * @param imageView Image message's ImageView.
             * @param string A file path, or a uri or url.
             */
            @Override
            public void loadImage(final ImageView imageView, String string) {
                // You can use other image load libraries.
                Log.e(TAG, "loadImage string : " + string);

                Glide.with(mContext)
                        .asBitmap()
                        .load(string)
                        .apply(new RequestOptions().fitCenter().placeholder(R.drawable.aurora_picture_not_found))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                int imageWidth = resource.getWidth();
                                int imageHeight = resource.getHeight();
                                Log.d(TAG, "Image width " + imageWidth + " height: " + imageHeight);

                                // 裁剪 bitmap
                                float width, height;
                                if (imageWidth > imageHeight) {
                                    if (imageWidth > MAX_WIDTH) {
                                        float temp = MAX_WIDTH / imageWidth * imageHeight;
                                        height = temp > MIN_HEIGHT ? temp : MIN_HEIGHT;
                                        width = MAX_WIDTH;
                                    } else if (imageWidth < MIN_WIDTH) {
                                        float temp = MIN_WIDTH / imageWidth * imageHeight;
                                        height = temp < MAX_HEIGHT ? temp : MAX_HEIGHT;
                                        width = MIN_WIDTH;
                                    } else {
                                        float ratio = imageWidth / imageHeight;
                                        if (ratio > 3) {
                                            ratio = 3;
                                        }
                                        height = imageHeight * ratio;
                                        width = imageWidth;
                                    }
                                } else {
                                    if (imageHeight > MAX_HEIGHT) {
                                        float temp = MAX_HEIGHT / imageHeight * imageWidth;
                                        width = temp > MIN_WIDTH ? temp : MIN_WIDTH;
                                        height = MAX_HEIGHT;
                                    } else if (imageHeight < MIN_HEIGHT) {
                                        float temp = MIN_HEIGHT / imageHeight * imageWidth;
                                        width = temp < MAX_WIDTH ? temp : MAX_WIDTH;
                                        height = MIN_HEIGHT;
                                    } else {
                                        float ratio = imageHeight / imageWidth;
                                        if (ratio > 3) {
                                            ratio = 3;
                                        }
                                        width = imageWidth * ratio;
                                        height = imageHeight;
                                    }
                                }
                                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                                params.width = (int) width;
                                params.height = (int) height;
                                imageView.setLayoutParams(params);
                                Matrix matrix = new Matrix();
                                float scaleWidth = width / imageWidth;
                                float scaleHeight = height / imageHeight;
                                matrix.postScale(scaleWidth, scaleHeight);
                                imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, imageWidth, imageHeight, matrix, true));
                            }
                        });
            }

            /**
             * Load video message
             * @param imageCover Video message's image cover
             * @param uri Local path or url.
             */
            @Override
            public void loadVideo(ImageView imageCover, String uri) {
                long interval = 5000 * 1000;
                Glide.with(mContext)
                        .asBitmap()
                        .load(uri)
                        // Resize image view by change override size.
                        .apply(new RequestOptions().frame(interval).override(200, 400))
                        .into(imageCover);
            }
        };

        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();

        // Use default layout
        mAdapter = new MsgListAdapter<JMUIMessage>("0", holdersConfig, imageLoader);
        // If you want to customise your layout, try to create custom ViewHolder:
        // holdersConfig.setSenderTxtMsg(CustomViewHolder.class, layoutRes);
        // holdersConfig.setReceiverTxtMsg(CustomViewHolder.class, layoutRes);
        // CustomViewHolder must extends ViewHolders defined in MsgListAdapter.
        // Current ViewHolders are TxtViewHolder, VoiceViewHolder.


        CustomMsgConfig receiveCustomMsgConfig = new CustomMsgConfig(IMessage.MessageType.RECEIVE_CUSTOM.ordinal(), R.layout.item_chat_system_notifacation, false, MyCustomerDefaultTxtViewHolder.class);
        CustomMsgConfig sendCustomMsgConfig = new CustomMsgConfig(IMessage.MessageType.SEND_CUSTOM.ordinal(), R.layout.item_chat_system_notifacation, true, MyCustomerDefaultTxtViewHolder.class);
        mAdapter.addCustomMsgType(receiveCustomMsgConfig.getViewType(), receiveCustomMsgConfig);
        mAdapter.addCustomMsgType(sendCustomMsgConfig.getViewType(), sendCustomMsgConfig);

        mAdapter.setOnMsgClickListener(new MsgListAdapter.OnMsgClickListener<JMUIMessage>() {
            @Override
            public void onMessageClick(JMUIMessage message) {
                Toast.makeText(mActivity, mActivity.getString(R.string.message_click_hint),
                        Toast.LENGTH_SHORT).show();
//                if (message.getType() == IMessage.MessageType.RECEIVE_VIDEO.ordinal()
//                        || message.getType() == IMessage.MessageType.SEND_VIDEO.ordinal()) {
//                    if (!TextUtils.isEmpty(message.getMediaFilePath())) {
////                        Intent intent = new Intent(mActivity, VideoActivity.class);
////                        intent.putExtra(VideoActivity.VIDEO_PATH, message.getMediaFilePath());
////                        mActivity.startActivity(intent);
//                    }
//                } else if (message.getType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()
//                        || message.getType() == IMessage.MessageType.SEND_IMAGE.ordinal()) {
//                    Intent intent = new Intent(mActivity, BrowserImageActivity.class);
//                    intent.putExtra("msgId", message.getMsgId());
//                    intent.putStringArrayListExtra("pathList", mPathList);
//                    intent.putStringArrayListExtra("idList", mMsgIdList);
//                    mActivity.startActivity(intent);
//                } else {
//                    Toast.makeText(mActivity,
//                            mActivity.getString(R.string.message_click_hint),
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });


        mAdapter.setOnAvatarClickListener(new MsgListAdapter.OnAvatarClickListener<JMUIMessage>() {
            @Override
            public void onAvatarClick(JMUIMessage message) {
                JMUserInfo userInfo = message.getFromUser();
                Toast.makeText(mActivity, mActivity.getString(R.string.avatar_click_hint),
                        Toast.LENGTH_SHORT).show();
                // Do something
            }
        });


        mAdapter.setMsgStatusViewClickListener(new MsgListAdapter.OnMsgStatusViewClickListener<JMUIMessage>() {
            @Override
            public void onStatusViewClick(JMUIMessage message) {
                Log.e(TAG, "setMsgStatusViewClickListener onStatusViewClick  message.getContentType(): " + message.getJMessage().getContentType()  );

                // message status view click, resend or download here
                //重发消息
                switch (message.getJMessage().getContentType()) {
                    case text:
                    case voice:
                        resendTextOrVoice( message);
                        break;
                    case image:
                        resendImage(message);
                        break;

                }
            }
        });

         mAdapter.setOnLoadMoreListener(new MsgListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalCount) {
                Log.i(TAG, "Loading next page : " + page + " totalCount : " + totalCount + "  mData.size() : " + mData.size());
                if (totalCount == mData.size()) {
                    loadNextPage();
                }
            }
        });
        mChatView.setAdapter(mAdapter);
        mAdapter.addToEnd(mData);
    }

    private static class MyCustomerDefaultTxtViewHolder extends MyCustomerViewHolder<IMessage> {

        public MyCustomerDefaultTxtViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);

        }
    }


    private void loadNextPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mConv != null) {
                    List<Message> msgList = mConv.getMessagesFromNewest(mStart, PAGE_MESSAGE_COUNT);
                    if (msgList != null) {
                        for (cn.jpush.im.android.api.model.Message msg : msgList) {
                            JMUIMessage jmuiMessage = new JMUIMessage(msg);
                            mData.add(0, jmuiMessage);
                        }
                        if (msgList.size() > 0) {
//                            checkSendingImgMsg();
                            mOffset = msgList.size();
                        } else {
                            mOffset = 0;
                        }
                        mStart += mOffset;
                        mAdapter.addToEnd(mData.subList(0, msgList.size()));
                    }
                }
            }
        }, 1000);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dismissSoftInput();
                view.clearFocus();

                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
        }
        return false;
    }

    /**
     * 接收消息类事件
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        final cn.jpush.im.android.api.model.Message msg = event.getMessage();
        //若为群聊相关事件，如添加、删除群成员
        Log.i(TAG, event.getMessage().toString());
        if (msg.getContentType() == ContentType.eventNotification) {
            GroupInfo groupInfo = (GroupInfo) msg.getTargetInfo();
            long groupId = groupInfo.getGroupID();
            EventNotificationContent.EventNotificationType type = ((EventNotificationContent) msg
                    .getContent()).getEventNotificationType();
            if (groupId == mGroupId) {
                switch (type) {
                    case group_member_added:
                        //添加群成员事件
                        List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                        //群主把当前用户添加到群聊，则显示聊天详情按钮
//                        refreshGroupNum();
                        if (userNames.contains(mMyInfo.getNickname()) || userNames.contains(mMyInfo.getUserName())) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    mChatView.showRightBtn();
                                }
                            });
                        }

                        break;
                    case group_member_removed:
                        //删除群成员事件
//                        userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
//                        //群主删除了当前用户，则隐藏聊天详情按钮
//                        if (userNames.contains(mMyInfo.getNickname()) || userNames.contains(mMyInfo.getUserName())) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mChatView.dismissRightBtn();
//                                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
//                                    if (TextUtils.isEmpty(groupInfo.getGroupName())) {
//                                        mChatView.setChatTitle(IdHelper.getString(mContext, "group"));
//                                    } else {
//                                        mChatView.setChatTitle(groupInfo.getGroupName());
//                                    }
//                                    mChatView.dismissGroupNum();
//                                }
//                            });
//                        } else {
//                            refreshGroupNum();
//                        }
                        break;
                    case group_member_exit:
//                        refreshGroupNum();
                        break;
                }
            }
        }
        //刷新消息
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //收到消息的类型为单聊
                if (msg.getTargetType() == ConversationType.single) {
                    UserInfo userInfo = (UserInfo) msg.getTargetInfo();
                    String targetId = userInfo.getUserName();
                     //判断消息是否在当前会话中
                    if (mIsSingle && targetId.equals(mTargetId) ) {
                        JMUIMessage jmuiMessage = new JMUIMessage(msg);
                        Log.i(TAG, "Receiving msg! " + msg);
                        mAdapter.addToStart(jmuiMessage, true);

//                        Message lastMsg = mAdapter.getLastMsg();
                        //收到的消息和Adapter中最后一条消息比较，如果最后一条为空或者不相同，则加入到MsgList
//                        if (lastMsg == null || msg.getId() != lastMsg.getId()) {

//                        } else {
//                            mAdapter.notifyDataSetChanged();
//                        }
                        scrollToBottom();
                    }

                } else {
                    Log.e(TAG, "unexpected!");
//                    long groupId = ((GroupInfo) msg.getTargetInfo()).getGroupID();
//                    if (groupId == mGroupId) {
//                        Message lastMsg = mChatAdapter.getLastMsg();
//                        if (lastMsg == null || msg.getId() != lastMsg.getId()) {
//                            mChatAdapter.addMsgToList(msg);
//                        } else {
//                            mChatAdapter.notifyDataSetChanged();
//                        }
//                    }
                }
            }
        });
    }

    private void scrollToBottom() {
        mChatView.mMsgList.requestLayout();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                 mChatView.mMsgList.smoothScrollToPosition(0);
                int  w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

                int  h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

                nestedScrollView.measure(w, h);

                int height =nestedScrollView.getMeasuredHeight();

                Log.e(TAG, "scrollToBottom   height : "+height);

                nestedScrollView.smoothScrollTo(0,height);
//                mAdapter.getLayoutManager().scrollToPosition(0);
             }
        }, 200);

    }

    private void dismissSoftInput() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ChatInputView chatInputView = mChatView.getChatInputView();
                if (chatInputView.getMenuState() == View.VISIBLE) {
                    chatInputView.dismissMenuLayout();
                }
                try {
                    View v = mActivity.getCurrentFocus();
                    if (mImm != null && v != null) {
                        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        if (mWindow != null)
                            mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void createSingleCustomMessage(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Log.e(TAG, "createSingleCustomMessage   ");
        CustomContent customContent = new CustomContent();
        customContent.setStringValue("text", text);
        Message message = mConv.createSendMessage(customContent);
        JMessageClient.sendMessage(message);
        final JMUIMessage msg = new JMUIMessage(message);
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addToStart(msg, true);
            }
        });
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int status, String desc) {
                mAdapter.updateMessage(msg);
                if (status == 0) {
                    Log.i(TAG, "send message succeed!");
                } else {
                    Log.i(TAG, "send message failed " + desc);
                }
            }
        });
    }

    private void resendTextOrVoice( final JMUIMessage msg) {


        if (!msg.getJMessage().isSendCompleteCallbackExists()) {
            msg.getJMessage().setOnSendCompleteCallback(new BasicCallback() {
                @Override
                public void gotResult(final int status, String desc) {

                    mAdapter.updateMessage(msg);
                }
            });
        }
        MessageSendingOptions options = new MessageSendingOptions();
        options.setNeedReadReceipt(true);
        JMessageClient.sendMessage(msg.getJMessage(), options);
    }

    private void resendImage( final JMUIMessage msg) {

        try {
            // 显示上传进度
            msg.getJMessage().setOnContentUploadProgressCallback(new ProgressUpdateCallback() {
                @Override
                public void onProgressUpdate(final double progress) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            msg.setProgress(Math.ceil(progress * 100) + "%");
                            Log.w(TAG, "Uploading image progress" + Math.ceil(progress * 100) + "%");
                            mAdapter.updateMessage(msg);
                         }
                    });
                }
            });
            if (!msg.getJMessage().isSendCompleteCallbackExists()) {
                msg.getJMessage().setOnSendCompleteCallback(new BasicCallback() {
                    @Override
                    public void gotResult(final int status, String desc) {

                        mAdapter.updateMessage(msg);
                    }
                });
            }
            MessageSendingOptions options = new MessageSendingOptions();
            options.setNeedReadReceipt(true);
            JMessageClient.sendMessage(msg.getJMessage(), options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
