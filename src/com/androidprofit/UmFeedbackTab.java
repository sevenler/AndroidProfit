
package com.androidprofit;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;

public class UmFeedbackTab extends Fragment implements IFragment {
	private FeedbackAgent agent;
	private Conversation defaultConversation;
	private ReplyListAdapter adapter;
	private ListView replyListView;
	RelativeLayout header;
	int headerHeight;
	int headerPaddingOriginal;
	EditText userReplyContentEdit;
	private String[] contactstle;
	private Spinner contact_key;
	private EditText contact_value;
	private ArrayAdapter<String> contact_adapter;
	private String mycontact;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = View.inflate(getActivity(), R.layout.umeng_fb_activity_conversation, null);
		initView(getActivity(), root);

		return root;
	}

	protected void initView(final Context ctx, View root) {
		try {
			contactstle = getResources().getStringArray(R.array.umeng_fb_contact_type_value);
			mycontact = contactstle[0] + ":";

			contact_key = (Spinner)root.findViewById(R.id.contact_key);
			contact_value = (EditText)root.findViewById(R.id.contact_value);
			contact_adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item,
					contactstle);
			contact_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			contact_key.setAdapter(contact_adapter);
			contact_key.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					mycontact = contactstle[arg2] + ":";
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}

			});
			agent = new FeedbackAgent(ctx);
			defaultConversation = agent.getDefaultConversation();

			replyListView = (ListView)root.findViewById(R.id.umeng_fb_reply_list);
			adapter = new ReplyListAdapter(ctx);
			replyListView.setAdapter(adapter);

			userReplyContentEdit = (EditText)root.findViewById(R.id.umeng_fb_reply_content);
			userReplyContentEdit.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					String ct = contact_value.getText().toString();
					if (ct == null || ct.equals("")) {
						return true;
					}
					UserInfo info = agent.getUserInfo();
					if (info == null) info = new UserInfo();
					Map<String, String> contact = info.getContact();
					if (contact == null) contact = new HashMap<String, String>();
					contact.put("plain", mycontact + ct);
					info.setContact(contact);
					agent.setUserInfo(info);
					return false;
				}
			});
			root.findViewById(R.id.umeng_fb_send).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String content = userReplyContentEdit.getEditableText().toString().trim();
					if (content == null || content.equals("")) return;

					userReplyContentEdit.getEditableText().clear();
					defaultConversation.addUserReply(content);

					sync();

					InputMethodManager imm = (InputMethodManager)ctx
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm != null)
						imm.hideSoftInputFromWindow(userReplyContentEdit.getWindowToken(), 0);
				}
			});

			root.findViewById(R.id.umeng_fb_back).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
				}
			});

			new Thread(new Runnable() {
				@Override
				public void run() {
					UserInfo info = agent.getUserInfo();
					if (info == null) info = new UserInfo();
					Map<String, String> remark = info.getRemark();
					if (remark == null) remark = new HashMap<String, String>();
					remark.put("Net", "Wifi");
					agent.setUserInfo(info);
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void sync() {
		Conversation.SyncListener listener = new Conversation.SyncListener() {

			@Override
			public void onSendUserReply(List<Reply> replyList) {
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onReceiveDevReply(List<DevReply> replyList) {
			}
		};
		defaultConversation.sync(listener);
	}

	class ReplyListAdapter extends BaseAdapter {
		Context mContext;
		LayoutInflater mInflater;

		public ReplyListAdapter(Context context) {
			this.mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			List<Reply> replyList = defaultConversation.getReplyList();
			return (replyList == null) ? 0 : replyList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.umeng_fb_list_item, null);

				holder = new ViewHolder();

				holder.replyDate = (TextView)convertView.findViewById(R.id.umeng_fb_reply_date);

				holder.replyContent = (TextView)convertView
						.findViewById(R.id.umeng_fb_reply_content);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}

			Reply reply = defaultConversation.getReplyList().get(position);

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);

			if (reply instanceof DevReply) {
				layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT); // ALIGN_PARENT_RIGHT
				holder.replyContent.setLayoutParams(layoutParams);

				// set bg after layout
				holder.replyContent.setBackgroundResource(R.drawable.background_tab);
			} else {
				layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); // ALIGN_PARENT_RIGHT
				// layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
				holder.replyContent.setLayoutParams(layoutParams);
				holder.replyContent.setBackgroundResource(R.drawable.background_tab);
			}

			holder.replyDate.setText(SimpleDateFormat.getDateTimeInstance().format(
					reply.getDatetime()));
			holder.replyContent.setText(reply.getContent());
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return defaultConversation.getReplyList().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			TextView replyDate;
			TextView replyContent;

		}
	}

	@Override
	public void onReflush() {
	}
}
