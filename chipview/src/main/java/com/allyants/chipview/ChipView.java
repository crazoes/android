package com.allyants.chipview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by jbonk on 1/13/2018.
 */

public class ChipView extends RelativeLayout{

    private FlexboxLayout flChips;
    public EditText etSearch;
    private ListView lvList;
    private ChipAdapter adapter;
    private SimpleSearchAdapter simpleSearchAdapter;

    public ChipView(Context context) {
        super(context);
        init(context, null);
    }

    public ChipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        View view = inflate(getContext(),R.layout.chip_view,this);
        flChips = view.findViewById(R.id.flChips);
        etSearch = view.findViewById(R.id.etSearch);
        lvList = view.findViewById(R.id.lvList);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChipView, 0, 0);

        String hint;

        try {
            hint = a.getString(R.styleable.ChipView_searchHint);
        }
        finally {
            a.recycle();
        }

        etSearch.setHint(hint);
    }

    public void setAdapter(ChipAdapter adapter){
        this.adapter = adapter;
        adapter.setChipView(this);
        simpleSearchAdapter = new SimpleSearchAdapter(getContext(),adapter);
        lvList.setAdapter(simpleSearchAdapter);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                simpleSearchAdapter.getFilter().filter(editable.toString());
            }
        });
    }

    public void notifyDataSetChanged(){
        refreshFlexbox();
        simpleSearchAdapter.notifyDataSetChanged();
    }

    private void refreshFlexbox(){
        for(int i = flChips.getChildCount() - 1; i >= 0;i--){
            if(flChips.indexOfChild(etSearch) != i){
                flChips.removeViewAt(i);
            }
        }
        for(int i = 0;i < adapter.getCount();i++){
            if(adapter.isSelected(i)) {
                View v = adapter.createChip(getContext(), i);
                flChips.addView(v,0);
            }
        }
    }
}
