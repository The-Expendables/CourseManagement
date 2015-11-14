package com.example.asus.coursemanagament.UiCustomViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.asus.coursemanagament.ActivityTeachingOffice.TaskManage.ListTeacherCourse;
import com.example.asus.coursemanagament.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwk on 2015/11/14.
 */
public class TeacherCourseListAdapter extends BaseAdapter implements Filterable {
    private MyFilter myFilter;
    private List<ListTeacherCourse> listInfos;
    private Context context;

    private ArrayList<ListTeacherCourse> mOriginalValues;

    private final Object mLock = new Object();

    public TeacherCourseListAdapter(Context context, List<ListTeacherCourse> listInfos){
        this.context = context;
        this.listInfos = listInfos;
    }


    @Override
    public int getCount() {
        return listInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return listInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_teacher_course,null);
            holder = new ViewHolder();

            holder.tv_courseName = (TextView)view.findViewById(R.id.courseName);
            holder.tv_choose = (TextView)view.findViewById(R.id.choose);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.tv_courseName.setText(listInfos.get(position).getCourseName());
        holder.tv_choose.setText(listInfos.get(position).getChoose());



        return view;
    }

    static class ViewHolder{
        TextView tv_courseName;
        TextView tv_choose;
    }
    @Override
    public Filter getFilter() {
        if(myFilter == null){
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    class MyFilter extends Filter{

        @Override
        //constranit 约束
        protected FilterResults performFiltering(CharSequence constraint) {
            // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    // 将list的用户 集合转换给这个原始数据的ArrayList
                    mOriginalValues = new ArrayList<ListTeacherCourse>(listInfos);
                }
            }

            if (constraint == null || constraint.length() == 0) {
                synchronized (mLock) {
                    ArrayList<ListTeacherCourse> list = new ArrayList<ListTeacherCourse>(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            }
            else{
                //做正式的筛选 prefix前缀
                String prefixString = constraint.toString().toLowerCase();

                // 声明一个临时的集合对象 将原始数据赋给这个临时变量
                final ArrayList<ListTeacherCourse> values = mOriginalValues;
                final int count = values.size();

                //新的集合对象
                final ArrayList<ListTeacherCourse> newValues = new ArrayList<ListTeacherCourse>(count);

                // 如果姓item中tv的前缀相符就添加到新的集合
                for (int i = 0; i < count; i++) {

                    final ListTeacherCourse value = (ListTeacherCourse) values.get(i);
                    //Android拼音帮助类PinyinUtils，getPingYin汉字转拼音
                    if (PinyinUtils.getPingYin(value.getCourseName()).startsWith(prefixString)||
                            PinyinUtils.getPingYin(value.getChoose()).startsWith(prefixString)||
                            value.getCourseName().startsWith(prefixString)||
                            value.getChoose().startsWith(prefixString)
                            ) {
                        newValues.add(value);
                    }
                    // 然后将这个新的集合数据赋给FilterResults对象
                    results.values = newValues;
                    results.count = newValues.size();
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //重新与适配器相关联的List重新赋值一下
            listInfos = (List<ListTeacherCourse>)results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }

    }
}
