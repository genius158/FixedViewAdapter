package com.yan.fixedviewadapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yan on 2016/6/12.
 * using this util can make that a few fixed views in a root viewGroup be using like listView;
 */
public abstract class FixedViewsAdapter<T> {
    private ViewGroup viewGroup;
    private View[] itemViewRoot;

    private List<T> tList;
    private int size;
    private int idSize;
    private OnItemClickListener onItemClickListener;
    private List<View[]> mCacheViews;

    private int type = 1;
    private int lines = 0;
    public static final int TYPE_GIRED = 2;
    public static final int TYPE_LINEAR = 1;


    public FixedViewsAdapter(int type, int lines, int allViewSize, View view, List<T> tList, int... viewIds) {
        consturctCommon(view, tList, viewIds);
        this.type = type;
        this.size = allViewSize;
        this.lines = lines;
        this.addAllView(viewIds);
        this.itemViewRoot = getItemViewRoots();
        this.notifyDataChange();
    }

    public FixedViewsAdapter(View view, List<T> tList, int... viewIds) {
        consturctCommon(view, tList, viewIds);
        this.itemViewRoot = getItemViewRoots();
        this.size = itemViewRoot.length;
        this.addAllView(viewIds);
        this.notifyDataChange();
    }

    private void consturctCommon(View view, List<T> tList, int... viewIds) {
        this.tList = tList;
        this.viewGroup = (ViewGroup) view;
        this.mCacheViews = new ArrayList<>();
        this.idSize = viewIds.length;

    }


    /**
     * view item setting
     */

    public void setOnClickListener(View view, View.OnClickListener onClickListener) {
        view.setOnClickListener(onClickListener);
    }

    public void setImageURL(View view, String str) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageURI(Uri.parse(str));
    }

    public void setText(View view, String str) {
        if (view instanceof TextView)
            ((TextView) view).setText(str);
    }

    public void setImage(View view, Bitmap bitmap) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageBitmap(bitmap);
    }

    public void setBackgroundColor(View view, int color) {
        view.setBackgroundColor(color);
    }

    public void setVisibility(View view, int visibility) {
        view.setVisibility(visibility);
    }


    public void setTextColor(View view, int color) {
        if (view instanceof TextView)
            ((TextView) view).setTextColor(color);
    }


    public void setBackgroundResource(View view, int resId) {
        view.setBackgroundResource(resId);
    }


    public void setBackgroundDrawable(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

    @TargetApi(16)
    public void setBackground(View view, Drawable drawable) {
        view.setBackground(drawable);
    }


    public void setImageBitmap(View view, Bitmap bitmap) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageBitmap(bitmap);
    }


    public void setImageResource(View view, int resId) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageResource(resId);
    }


    public void setImageDrawable(View view, Drawable drawable) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageDrawable(drawable);
    }

    @TargetApi(16)
    public void setImageAlpha(View view, int alpha) {
        if (view instanceof ImageView)
            ((ImageView) view).setImageAlpha(alpha);
    }

    public void setChecked(View view, boolean checked) {
        if (view instanceof Checkable)
            ((Checkable) view).setChecked(checked);
    }


    public void setProgress(View view, int progress) {
        if (view instanceof ProgressBar)
            ((ProgressBar) view).setProgress(progress);
    }

    public void setProgress(View view, int progress, int max) {
        if (view instanceof ProgressBar)
            ((ProgressBar) view).setProgress(progress);
    }

    public void setMax(View view, int max) {
        if (view instanceof ProgressBar)
            ((ProgressBar) view).setMax(max);
    }

    public void setRating(View view, float rating) {
        if (view instanceof RatingBar)
            ((RatingBar) view).setRating(rating);
    }


    public void setRating(View view, float rating, int max) {
        if (view instanceof RatingBar) {
            ((RatingBar) view).setRating(max);
            ((RatingBar) view).setRating(rating);
        }
    }


    public void setOnTouchListener(View view, View.OnTouchListener listener) {
        view.setOnTouchListener(listener);
    }


    public void setOnLongClickListener(View view, View.OnLongClickListener listener) {
        view.setOnLongClickListener(listener);
    }


    public void setViewEnable(View view, boolean enable) {
        view.setEnabled(enable);
    }

    public void setClickable(View view, boolean clickable) {
        view.setClickable(clickable);
    }

    public void setTextSize(View view, float size) {
        if (view instanceof View)
            ((TextView) view).setTextSize(size);
    }

    /**
     *   setting about view
     * ----------------------------------------------------
     */

    /**
     * data bind
     *
     * @param view return view
     * @param obj  return data that need to bind
     */
    public abstract void onBindViewHodler(View[] view, T obj);

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
        for (int i = 0; i < size; i++) {
            final int finalI = i;
            itemViewRoot[i].setClickable(true);
            itemViewRoot[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(finalI);
                }
            });
        }
    }

    /**
     * refresh data notify view
     */
    public void notifyDataChange() {
        for (int i = 0; i < size; i++) {
            if (i >= tList.size()) {
                itemViewClose(itemViewRoot[i]);
            } else {
                itemViewRoot[i].setVisibility(View.VISIBLE);
                itemViewShow(itemViewRoot[i]);
                onBindViewHodler(getViews(i), tList.get(i));
            }
        }
    }


    public void addData(int position, T t) {
        tList.add(position, t);
        itemViewShow(itemViewRoot[position]);
    }

    public int getViewCount() {
        return idSize;
    }

    public boolean isDataFill() {
        return (tList.size() >= size);
    }


    private int itemAnimationDuration = 400;

    public void setItemAnimationDuration(int itemAnimationDuration) {
        this.itemAnimationDuration = itemAnimationDuration;
    }


    public final static int ANIMATIONFINISH_SHOW = 1;
    public final static int ANIMATIONFINISH_HIDE = 2;

    public interface OnAnimationFinish {
        void onFinish(int animationType);
    }

    private OnAnimationFinish onAnimationFinish;

    public void setOnAnimationFinish(OnAnimationFinish onAnimationFinish) {
        this.onAnimationFinish = onAnimationFinish;
    }

    private void itemViewShow(final View view) {
        view.setVisibility(View.VISIBLE);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator = ValueAnimator.ofFloat(0, 1);

        valueAnimator.setDuration(itemAnimationDuration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((float) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (onAnimationFinish != null) {
                    onAnimationFinish.onFinish(1);
                }
                view.setVisibility(View.VISIBLE);
            }
        });
        valueAnimator.start();
        {
      /*  //  ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(itemAnimationDuration).start();
        Animation animation = new AlphaAnimation(0, 1) {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
            }
        };
        animation.setDuration(itemAnimationDuration);
        view.startAnimation(animation);*/
        }
    }


    private void itemViewClose(final View view) {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator = ValueAnimator.ofFloat(1, 0);

        valueAnimator.setDuration(itemAnimationDuration);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((float) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (onAnimationFinish != null) {
                    onAnimationFinish.onFinish(2);
                }
                view.setVisibility(View.GONE);
            }
        });
        valueAnimator.start();
      /*  Animation animation = new AlphaAnimation(1, 0) {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                }
            }
        };
        animation.setDuration(itemAnimationDuration);
        view.startAnimation(animation);*/
    }


    private View[] getViews(int position) {
        View[] views = new View[idSize];
        for (int j = 0; j < idSize; j++) {
            views[j] = mCacheViews.get(j)[position];
        }
        return views;
    }


    private void addAllView(int... viewIds) {
        if (type == TYPE_GIRED) {
            View[][] viewDouble = new View[lines][];

            for (int j = 0; j < viewIds.length; j++) {
                for (int i = 0; i < lines; i++) {
                    viewDouble[i] = utilGetViews((ViewGroup) viewGroup.getChildAt(i), size / lines, viewIds[j]);
                }
                View[] tempView = new View[size];
                int index = 0;
                for (int z = 0; z < lines; z++) {
                    for (View view : viewDouble[z]) {
                        tempView[index++] = view;
                    }
                }
                mCacheViews.add(tempView);
            }
        } else
            for (int viewId : viewIds) {
                View[] views = utilGetViews(viewGroup, size, viewId);
                mCacheViews.add(views);
            }
    }

    private View[] getItemViewRoots() {
        View[] views = new View[size];
        if (type == TYPE_GIRED) {
            int viewIndex = 0;
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < size / lines; j++) {
                    views[viewIndex++] = ((ViewGroup) viewGroup.getChildAt(i)).getChildAt(j);
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                views[i] = viewGroup.getChildAt(i);
            }
        }
        return views;
    }

    /**
     * analyze view
     * --------------------------------------------
     */
    private View[] utilGetViews(ViewGroup viewGroup, int size, int viewId) {
        LinkedList<Integer> integers = utilGetChildViewPositions(viewGroup.getChildAt(0), viewId);
        View[] views = new View[size];
        for (int i = 0; i < size; i++) {
            views[i] = utilFindView(viewGroup.getChildAt(i), integers);
        }
        return views;
    }

    private View utilFindView(View view, List<Integer> position) {
        for (int i = 0; i < position.size(); i++)
            view = ((ViewGroup) view).getChildAt(position.get(i));
        return view;
    }

    private LinkedList<Integer> utilGetChildViewPositions(View viewGroup, int viewId) {
        if (!(viewGroup instanceof ViewGroup)) return null;
        LinkedList<Integer> positions = new LinkedList<>();
        utilGetChildViewPosition(viewGroup, viewId, positions);
        return positions;
    }

    private boolean utilGetChildViewPosition(View viewGroup, int viewId, LinkedList<Integer> positions) {
        for (int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++) {
            positions.addLast(i);
            int id = ((ViewGroup) viewGroup).getChildAt(i).getId();
            if (id == viewId) return true;
            if (((ViewGroup) viewGroup).getChildAt(i) instanceof ViewGroup) {
                if (utilGetChildViewPosition(((ViewGroup) viewGroup).getChildAt(i), viewId, positions))
                    break;
            } else {
                positions.removeLast();
                if (i == ((ViewGroup) viewGroup).getChildCount() - 1 && positions.size() != 0) {
                    positions.removeLast();
                }
            }
        }
        return false;
    }
/**
 * -----------------------------------------------------------------------------------------
 * end
 */

}
