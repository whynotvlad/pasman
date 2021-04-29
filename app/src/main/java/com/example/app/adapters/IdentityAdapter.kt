package com.happyplaces.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.app.*
import com.example.app.database.DatabaseIdentity
import com.example.app.models.IdentityModel
import kotlinx.android.synthetic.main.item_identity.view.*

// TODO (Step 6: Creating an adapter class for binding it to the recyclerview in the new package which is adapters.)
// START
open class IdentityAdapter(

    private val context: Context,
    private var list: ArrayList<IdentityModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_identity,
                parent,
                false
            )
        )
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is ViewHolder) {
            holder.itemView.tv_title_item_identity.text = model.name
            holder.itemView.tv_email_item_identity.text = model.email
            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.OnClick(position, model)
                }
            }
        }
    }

    interface OnClickListener {
        fun OnClick(position: Int, model: IdentityModel)
    }

    /**
     * Gets the number of items in the list
     */


    fun deleteIdentity(holder: ViewHolder) {
        val dbHandler = DatabaseIdentity(context)
        val isDeleted = dbHandler.deleteIdentity(list[holder.adapterPosition])
        if (isDeleted > 0) {
            list.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    // Edit needs fix blyat
    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, AddIdentityActivity::class.java)
        intent.putExtra(IdentityActivity.EXTRA_IDENTITIES_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val background: ConstraintLayout = view.findViewById(R.id.identity_background)
        val foreground: CardView = view.findViewById(R.id.identity_foreground)
    }
}
// END