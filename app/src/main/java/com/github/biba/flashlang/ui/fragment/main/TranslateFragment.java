package com.github.biba.flashlang.ui.fragment.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.ui.contract.TranslateContract;
import com.github.biba.flashlang.ui.dialog.LanguagesDialogBuilder;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.presenter.TranslatePresenter;
import com.github.biba.lib.utils.StringUtils;

import java.util.List;

public class TranslateFragment extends Fragment implements View.OnClickListener, TranslateContract.View {

    public static final String TRANSLATION_KEY = "TRANSLATION";
    private ILanguage mSourceLanguage;
    private ILanguage mTargetLanguage;
    private View mView;
    private TextView mSourceLanguageTextView;
    private TextView mTargetLanguageTextView;
    private EditText mSourceEditText;
    private TextView mTranslatedTextView;
    private View mTranslationViewContainer;
    private View mSwapLanguagesView;
    private View mTranslateButton;
    private String mTranslation;
    private View mProgressBar;

    TranslateContract.Presenter mPresenter;
    private AlertDialog mChooseLanguageDialog;

    public TranslateFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (mPresenter == null) {
            mPresenter = new TranslatePresenter();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_translate, pContainer, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View pView, @Nullable final Bundle pSavedInstanceState) {
        super.onViewCreated(pView, pSavedInstanceState);
        mView = pView;
        init();
        if (pSavedInstanceState != null) {
            mTranslation = pSavedInstanceState.getString(TRANSLATION_KEY);
        }
    }

    private void init() {
        mTranslateButton = mView.findViewById(R.id.translate_button);
        mTranslateButton.setOnClickListener(this);
        mTargetLanguageTextView = mView.findViewById(R.id.target_languages_text_view);
        mTargetLanguageTextView.setOnClickListener(this);
        mSourceLanguageTextView = mView.findViewById(R.id.source_languages_text_view);
        mSourceLanguageTextView.setOnClickListener(this);
        mSwapLanguagesView = mView.findViewById(R.id.swap_languages_image_view);
        mSwapLanguagesView.setOnClickListener(this);
        mView.findViewById(R.id.translation_close_image_view).setOnClickListener(this);
        mSourceEditText = mView.findViewById(R.id.source_text_edit_text);
        mTranslatedTextView = mView.findViewById(R.id.translated_text_text_view);
        mTranslationViewContainer = mView.findViewById(R.id.translate_view_container);
        mProgressBar = mView.findViewById(R.id.translate_progress_bar);

        initSourceEditText();
        initTranslationView();
        initLanguagesViews();
    }

    void initTranslationView() {
        if (mTranslation != null) {
            showTranslationView(mTranslation);
        }
    }

    void initLanguagesViews() {
        if (mSourceLanguage != null) {
            mSourceLanguageTextView.setText(mSourceLanguage.getLanguageName());
        }
        if (mTargetLanguage != null) {
            mTargetLanguageTextView.setText(mTargetLanguage.getLanguageName());
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initSourceEditText() {
        mSourceEditText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (mTranslationViewContainer.getVisibility() == View.VISIBLE) {
                    closeTranslation();
                }
                return false;
            }
        });

        mSourceEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (StringUtils.isNullOrEmpty(s.toString())) {
                    mTranslateButton.setEnabled(false);
                } else {
                    mTranslateButton.setEnabled(true);
                }

            }
        });
    }

    @Override
    public void onTranslationSuccess(final String pTranslatedText) {
        showTranslationView(pTranslatedText);
        showSnackbar(R.string.card_saved_text);
        mTranslation = pTranslatedText;
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onTranslateError(final String pErrorMessage) {
        showTranslationError(pErrorMessage);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public String getSourceLanguage() {
        if (mSourceLanguage == null) {
            throw new IllegalStateException("Choose source language");
        }
        return mSourceLanguage.getLanguageCode();
    }

    @Override
    public String getSourceText() {
        return mSourceEditText.getText().toString();
    }

    @Override
    public String getTargetLanguage() {
        if (mTargetLanguage == null) {
            throw new IllegalStateException("Choose target language");
        }
        return mTargetLanguage.getLanguageCode();
    }

    @Override
    public void onClick(final View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.source_languages_text_view:
                showSourceLanguageDialog();
                break;
            case R.id.swap_languages_image_view:
                swapLanguages();
                break;
            case R.id.target_languages_text_view:
                showTargetLanguageDialog();
                break;
            case R.id.translate_button:
                mProgressBar.setVisibility(View.VISIBLE);
                translate();
                break;
            case R.id.translation_close_image_view:
                closeTranslation();
                break;
        }
    }

    private void closeTranslation() {
        mTranslationViewContainer.setVisibility(View.GONE);
        mTranslateButton.setVisibility(View.VISIBLE);
        mTranslatedTextView.setText("");
        setUpperBarEnabled(true);
    }

    private void translate() {
        mPresenter.translate();
    }

    private void showTargetLanguageDialog() {

        showChooseLanguageDialog(new IRecycleClickCallback<ILanguage>() {

            @Override
            public void onClick(final ILanguage pLanguage) {
                mTargetLanguage = pLanguage;
                mTargetLanguageTextView.setText(pLanguage.getLanguageName());
                mChooseLanguageDialog.dismiss();
            }

        });

    }

    private void swapLanguages() {
        applySwapLanguagesAnimation();
        final String sourceText = mSourceLanguageTextView.getText().toString();
        final String targetText = mTargetLanguageTextView.getText().toString();

        mTargetLanguageTextView.setText(sourceText);
        mSourceLanguageTextView.setText(targetText);

        final ILanguage tempLanguage = mTargetLanguage;
        mTargetLanguage = mSourceLanguage;
        mSourceLanguage = tempLanguage;

    }

    private void showSourceLanguageDialog() {
        showChooseLanguageDialog(new IRecycleClickCallback<ILanguage>() {

            @Override
            public void onClick(final ILanguage pLanguage) {
                mSourceLanguage = pLanguage;
                mSourceLanguageTextView.setText(pLanguage.getLanguageName());
                mChooseLanguageDialog.dismiss();
            }
        });
    }

    private void showTranslationView(final CharSequence pTranslatedText) {
        mTranslateButton.setVisibility(View.GONE);
        mTranslationViewContainer.setVisibility(View.VISIBLE);
        mTranslatedTextView.setText(pTranslatedText);
        setUpperBarEnabled(false);
    }

    private void showTranslationError(final String pErrorMessage) {
        showSnackbar(pErrorMessage);
    }

    private void applySwapLanguagesAnimation() {
        final Animation rotateAnimation = AnimationUtils.loadAnimation(this.getContext(), R.anim.rotate360);
        rotateAnimation.setRepeatCount(1);
        mSwapLanguagesView.startAnimation(rotateAnimation);
    }

    private void setUpperBarEnabled(final boolean pEnabled) {
        mSourceLanguageTextView.setEnabled(pEnabled);
        mTargetLanguageTextView.setEnabled(pEnabled);
        mSwapLanguagesView.setEnabled(pEnabled);
    }

    private void showSnackbar(final int pStringResId) {
        Snackbar.make(mView, pStringResId, Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackbar(final CharSequence pString) {
        Snackbar.make(mView, pString, Snackbar.LENGTH_SHORT).show();
    }

    private void showChooseLanguageDialog(final IRecycleClickCallback<ILanguage> pCallback) {
        final List<ILanguage> languages = mPresenter.loadSupportedLanguages();
        final LanguagesDialogBuilder builder = new LanguagesDialogBuilder(this.getContext());
        builder.setLanguages(languages);
        builder.setChoiceCallback(pCallback);
        mChooseLanguageDialog = builder.create();
        mChooseLanguageDialog.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TRANSLATION_KEY, mTranslation);
    }
}
