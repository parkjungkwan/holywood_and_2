package app.demo.com.contacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static app.demo.com.contacts.Index.*;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        Context _this = MemberDetail.this;
        Intent intent = this.getIntent();
        String seq = intent.getExtras().getString("seq");
        Toast.makeText(_this,"넘어온 시퀀스: "+seq,Toast.LENGTH_LONG).show();
        ItemDetail query = new ItemDetail(_this);
        query.id = seq;
        Member m = (Member)new ISupplier() {
            @Override
            public Object get() {
                return query.get();
            }
        }.get();
        ImageView photo = findViewById(R.id.profile);
        photo.setImageDrawable(
                getResources()
                        .getDrawable(
                                getResources()
                                        .getIdentifier(
                                                _this.getPackageName()+":drawable/"
                                                        +((String)new Index.ISupplier() {
                                                    @Override
                                                    public Object get() {
                                                        return query.get();
                                                    }
                                                }.get()),
                                                null,
                                                null),
                                _this.getTheme()
                        )
        );
        TextView name = findViewById(R.id.name);
        name.setText(m.name);
        TextView phone = findViewById(R.id.phone);
        phone.setText(m.phone);
        TextView email = findViewById(R.id.email);
        email.setText(m.email);
        TextView addr = findViewById(R.id.addr);
        addr.setText(m.addr);
        findViewById(R.id.callBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.dialBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.smsBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.emailBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.albumBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.movieBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.mapBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.musicBtn).setOnClickListener(
                (v)->{}
        );
        findViewById(R.id.updateBtn).setOnClickListener(
                (v)->{
                    Intent intent2 = new Intent(_this, MemberUpdate.class);
                    intent2.putExtra("spec",
                            m.seq+","+
                                m.name+","+
                                m.pw+","+
                                m.email+","+
                                m.phone+","+
                                m.addr+","+
                                m.photo
                    );
                    startActivity(intent2);

                });
        findViewById(R.id.listBtn).setOnClickListener(
                (v)->{
                    startActivity(new Intent(_this, MemberList.class));
                }
        );


    }
    private class DetailQuery extends QueryFactory{
        SQLiteOpenHelper helper;
        public DetailQuery(Context _this) {
            super(_this);
            helper = new SQLiteHelper(_this);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }
    private class ItemDetail extends DetailQuery{
        String id;
        public ItemDetail(Context _this) {
            super(_this);
        }
        public Member get(){
            Member m = null;
            Cursor c = getDatabase()
                    .rawQuery(
                        String.format(
                                " SELECT * FROM %s WHERE %s LIKE '%s' "
                                ,MEMBERS, MSEQ, id),null);

                if(c != null && c.moveToNext()){
                    m = new Member();
                    m.seq = Integer.parseInt(c.getString(c.getColumnIndex(MSEQ)));
                    m.name = c.getString(c.getColumnIndex(MNAME));
                    m.pw = c.getString(c.getColumnIndex(MPW));
                    m.email = c.getString(c.getColumnIndex(MEMAIL));
                    m.phone = c.getString(c.getColumnIndex(MPHONE));
                    m.addr = c.getString(c.getColumnIndex(MEMAIL));
                    m.photo = c.getString(c.getColumnIndex(MPHOTO));
                }
            return m;
        }
    }
}
