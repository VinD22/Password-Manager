package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import manager.password.app.v.passwordmanager.R;
import model.AccountDetails;

/**
 * Adapter class to maintain list of account details
 */

public class AccountDetailsAdapter extends RecyclerView.Adapter<AccountDetailsAdapter.RecyclerViewHolder> {

    private List<AccountDetails> data, dataCopy;
    private Context mContext;
     Realm realm;

    public AccountDetailsAdapter(Context context, ArrayList<AccountDetails> data) {
        this.mContext = context;
        this.data = data;
        dataCopy = new ArrayList<AccountDetails>();
        dataCopy.addAll(data);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.account_detail_list_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {

        final AccountDetails tempAccountDetails = data.get(viewHolder.getAdapterPosition());

//        viewHolder.mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                new AlertDialog.Builder(mContext, R.style.MyDialogTheme)
//                        .setTitle(R.string.delete_transaction)
//                        .setMessage(R.string.confirm_delete)
//                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                final AccountDetails tempAccountDetails = data.get(viewHolder.getAdapterPosition());
//                                int transactionId = tempAccountDetails.getId();
//
//                                // Delete from Arraylist
//                                data.remove(viewHolder.getAdapterPosition());
//                                notifyDataSetChanged();
//
//                                // Delete from Realm
//                                Realm realm = Realm.getDefaultInstance();
//                                realm.beginAccountDetails();
//                                AccountDetails deleteAccountDetails = realm.where(AccountDetails.class).equalTo("id", transactionId).findFirst();
//                                deleteAccountDetails.deleteFromRealm();
//                                realm.commitAccountDetails();
//
//                            }
//                        })
//                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                            }
//                        })
//                        .show();
//
//                // Toast.makeText(mContext, "Long Clicked!", Toast.LENGTH_SHORT).show(); // working!
//                return true;
//            }
//        });


    }

    public void filter(String text) {
        // Toast.makeText(mContext, "" + text + " /// " + dataCopy.size()  , Toast.LENGTH_SHORT).show();
        data.clear();
        if(text.isEmpty()){
            data.addAll(dataCopy);
        } else {
            data.clear();
            text = text.toLowerCase();
            for(AccountDetails item: dataCopy){
                if(item.getTitle().toLowerCase().contains(text)){
                    data.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout placeHolder;
        public LinearLayout mLinearLayout;
        protected TextView mAccountDetailsName, mAccountDetailsAmount;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lin);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            mAccountDetailsName = (TextView) itemView.findViewById(R.id.transaction_name);
            mAccountDetailsAmount = (TextView) itemView.findViewById(R.id.transaction_amount);
        }

    }


}
