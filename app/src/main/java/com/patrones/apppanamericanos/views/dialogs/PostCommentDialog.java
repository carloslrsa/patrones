package com.patrones.apppanamericanos.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.models.dao.design.async.ICommentAsyncDAO;
import com.patrones.apppanamericanos.models.entities.Comment;
import com.patrones.apppanamericanos.models.session.SessionManager;
import com.patrones.apppanamericanos.models.session.commands.LoadNamesCommand;
import com.patrones.apppanamericanos.models.session.commands.LoadUserCommand;
import com.patrones.apppanamericanos.utils.callbacks.SimpleCallback;
import com.patrones.apppanamericanos.utils.factory.dao.FactoryAsyncDAO;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostCommentDialog extends Dialog {

    @BindView(R.id.title_postcomment_et)
    EditText titleParm;
    @BindView(R.id.message_postcomment_et)
    EditText messageParm;
    @BindView(R.id.post_postcomment_btn)
    MaterialButton postBtn;

    private ICommentAsyncDAO commentDAO;

    private String eventId;
    private String userId;
    private String userName;

    public PostCommentDialog(@NonNull Context context, String eventId  ) {
        super(context);
        this.eventId = eventId;
    }

    public PostCommentDialog(@NonNull Context context, int themeResId, String eventId ) {
        super(context, themeResId);
        this.eventId = eventId;
    }

    protected PostCommentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, String eventId  ) {
        super(context, cancelable, cancelListener);
        this.eventId = eventId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_comment_event);

        Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean publish = true;
                if (titleParm.getText().toString().isEmpty()) {
                    titleParm.setError("Debe ingresar un título");
                    publish = false;
                }
                if(messageParm.getText().toString().isEmpty()){
                    messageParm.setError("Debe ingresar un comentario");
                    publish = false;
                }

                if(publish){
                    messageParm.setError(null);
                    titleParm.setError(null);
                    messageParm.setEnabled(false);
                    titleParm.setEnabled(false);
                    postBtn.setEnabled(false);

                    userId = SessionManager.getInstance().executeCommand(new LoadUserCommand(getContext())).getDni();

                    commentDAO = FactoryAsyncDAO.getInstance(getContext()).getCommentaryDAO();
                    commentDAO.insert(new Comment("", userId, SessionManager.getInstance().executeCommand(new LoadNamesCommand(getContext())), eventId, new Date(System.currentTimeMillis()), titleParm.getText().toString(), messageParm.getText().toString()),
                            new SimpleCallback<Comment>() {
                                @Override
                                public void OnResult(Comment result) {
                                    Toast.makeText(getContext(), "Comentario publicado con éxito", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }

                                @Override
                                public void OnFailure(String message) {
                                    Toast.makeText(getContext(), "No se pudo publicar su comentario", Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            });
                }
            }
        });
    }
}
