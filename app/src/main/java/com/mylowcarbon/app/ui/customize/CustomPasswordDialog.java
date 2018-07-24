package com.mylowcarbon.app.ui.customize;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.mylowcarbon.app.R;


public class CustomPasswordDialog extends Dialog {

	public CustomPasswordDialog(Context context) {
		super(context);
	}

	public CustomPasswordDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private TextView tv_title;
//		private TextView tv_message;
//		private ImageView icon_tip ;
		// private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;

		private PasswordInputView passwordInputView;
		private TextWatcher mTextWatcher ;
		public Builder(Context context, TextWatcher watcher) {
			this.context = context;
			this.mTextWatcher = watcher;
		}

		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		CustomPasswordDialog mDialog;

		public CustomPasswordDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mDialog = new CustomPasswordDialog(context, R.style.passwordDialog);

//			if (mDialog!=null) {
//				mDialog.dismiss();
//				mDialog=null;
//			}
//			mDialog=new CustomPasswordDialog(context,R.style.Dialog);
//			mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
//			mDialog.getWindow().setFlags(
//				    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//				    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//			mDialog.setView(new EditText(mContext));
//			mDialog.show();
//	        Window window = mDialog.getWindow();
//	        WindowManager.LayoutParams lp = window.getAttributes();
//	        window.setGravity(Gravity.CENTER);
//	        lp.y = -150; // 新位置Y坐标
//	        window.setAttributes(lp);
//			window.setContentView(R.layout.custom_password_dialog);
//			mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//			mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//	                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);






			View window = inflater.inflate(R.layout.custom_password_dialog,
					null);
			mDialog.addContentView(window, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			 tv_title= (TextView) window.findViewById(R.id.title) ;
//			tv_message = (TextView) layout.findViewById(R.id.message);
//			icon_tip = (ImageView) window.findViewById(R.id.icon_tip);
			passwordInputView = (PasswordInputView) window.findViewById(R.id.passwordInputView);
			passwordInputView.setPasswordLength(4);
			passwordInputView.addTextChangedListener(mTextWatcher);
			if (negativeButtonText != null) {
				((Button) window.findViewById(R.id.cancel))
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) window.findViewById(R.id.cancel))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(mDialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				window.findViewById(R.id.cancel).setVisibility(View.GONE);
			}

			if (positiveButtonText != null) {
				((Button) window.findViewById(R.id.confirm))
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					((Button) window.findViewById(R.id.confirm))
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(mDialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				window.findViewById(R.id.confirm).setVisibility(View.GONE);
			}
			// if (message != null) {
			// ((TextView) layout.findViewById(R.id.message)).setText(message);
			// }

			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(false);
//			mDialog.setContentView(window);
			return mDialog;
		}

		public boolean isShowing() {
			return mDialog.isShowing();
		}

		 public void setTittle(String tittle) {
		 if (tv_title != null) {
		 tv_title.setText(tittle);
		 }

		 }
//		public void setMessage(String message) {
//			if (tv_message != null) {
//				tv_message.setText(message);
//			}
//
//		}
//		public void setIconVisibility(Boolean visibility) {
//			if (icon_tip != null) {
//				Log.d("setIconVisibility", "visibility="+visibility);
//				if (visibility) {
//					icon_tip.setVisibility(View.VISIBLE);
//				}else {
//					icon_tip.setVisibility(View.GONE);
//				}
//
//			}
//
//		}
	}
}
