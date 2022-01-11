# MOLE GAME

java - node.js - Mysql을 이용해서 서버를 사용해 멀티 플레이를 즐길 수 있는 두더지 잡기 게임 구현. 

- Component
    - Database
        
        Login 정보를 저장하기 위한 db와 user의 정보(스코어, 캐릭터 색상)을 저장할 수 있는 db 두 가지를 사용.
        
        ```jsx
        var express = require('express');
        var app = express();
        var userdb_config = require(__dirname + '/config/database.js');
        var conn = userdb_config.init();
        
        var gamedb_config = require(__dirname + '/config/gameinfo.js');
        var conn2 = gamedb_config.init();
        ```

    
- In Game
    - LogIn
        
        retrofit을 사용해서 회원가입 시 각 유저들의 ID/PWD를 저장한다. 로그인할 때는 mysql의 query문을 통해 해당 id가 존재하는지 체크한 후 비밀번호를 체크한다.
        
        ```jsx
        public void onClick(View view){
            switch (view.getId()){
                case R.id.main_loginButton:
                    System.out.println("we are here");
                    s_id = id.getText().toString();
                    s_pwd = pwd.getText().toString();
                    Login_Info login_info = new Login_Info(s_id, s_pwd);
        
                    Call<ResponseBody> call_post = myInterface.logIn(s_id, s_pwd);
        
                    call_post.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    assert response.body() != null;
                                    String result = response.body().string();
                                    Log.v(TAG, "result = " + result);
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                User_Info userinfo = new User_Info();
                                userinfo.set_login_check(true);
                                userinfo.setUserId(s_id);
        ```
        
    - 캐릭터 & 게임 옵션
        
        연락처를 클릭하게 되면 옵션이 나타나는 효과가 있다. 이 때 recyclerview 내부의 viewholder들의 position이 모두 바뀌게 된다. 클릭해서 옵션이 나타날 때와 다른 아이템을 클릭해서 옵션이 사라질때를 위해 현재 poisiton과 이전 position(preposition)을 저장해둔다. changeVisibilitiy는 옵션이 있는 경우, position에 대한 정보를 삭제시켜 모든 item들을 위로 올리는 동작을 수행하고, 다른 아이템을 클릭하는 경우는 해당 바를 없애고 클릭한 item에 해당하는 옵션바를 새로 나타나게 하는 동작을 수행한다.
        
        ```java
        case R.id.linearLayout:
                            m_number = number.getText().toString();
                            if (selectedItems.get(position)) {
                                // 펼쳐진 Item을 클릭 시
                                selectedItems.delete(position);
                            } else {
                                ////문제x///
                                // 직전의 클릭됐던 Item의 클릭상태를 지움
                                selectedItems.delete(preposition);
                                // 클릭한 Item의 position을 저장
                                selectedItems.put(position, true);
                            }
                            // 해당 포지션의 변화를 알림
                            if (preposition != -1) {
                                notifyItemChanged(preposition);
                            }
                            notifyItemChanged(position);//리스트 갱신
                            // 클릭된 position 저장
                            preposition = position;
                            break;
        ```
        
        ```java
        void changeVisibility(final boolean isExpanded) {
                    int dpValue = 50;
                    float d = context.getResources().getDisplayMetrics().density;
                    int height = (int) (dpValue * d);
        
                    ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0); //고치
                    va.setDuration(500);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            calllayout.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                            calllayout.requestLayout();
                            calllayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                        }
                    });
                    va.start();
        ```
        
        more  버튼을 누르면 해당 연락처를 modify, delete할 수 있도록 레이아웃이 하나 더 나타난다. 이 레이아웃의 구현은 setvisibility를 이용해서 구현했다. more 버튼을 누른 상태에서 다른 아이템을 누르면 modify/delete 레이아웃도 함께 사라져야 하므로, 이를 구분할 수 있는 reference로 boolean  타입인 check을 사용한다.
        
        ```java
        case R.id.morebutton:
                            if (check == false){
                                option.setVisibility(option.VISIBLE);
                                check = true;
                            }
                            else{
                                option.setVisibility(option.GONE);
                                check = false;
                            }
                            break;
        ```
        
    - 유저는 캐릭터의 색상을 선택할 수 있다. 유저의 모든 행동은 db에 기록되며, 게임 대기실 화면에서도 색상과 캐릭터를 유지할 수 있다. room create 시, db 내부에 ID, COLOR, SCORE를 필드로 갖는 새로운 테이블이 생성되고 방장의 정보가 기록된다. 해당 방으로 다른 유저가 들어가는 순간, db에 해당 유저의 정보가 기록된다. 게임 대기실에서 각 유저들은 나머지 유저들의 정보를 서버로부터 받아야하므로, 서버와 클라이언트의 소켓통신을 이용한다.
    
    ```jsx
    public class SocketInterface{
        private static Socketmysocket= null;
        private static Activityact;
        public SocketInterface(Activity activity) {
            if(act== null){
    act= activity;
            }
        }
    
        public Socket getInstance() {
            if (mysocket== null) {
                try {
                    URL url = new URL("http://192.249.18.122:443");
    mysocket= IO.socket(url.toURI());
    mysocket.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            returnmysocket;
        }
    
        public void joinroom() {
            JSONObject data = new JSONObject();
            try {
                User_Info userinfo = new User_Info();
                String userid = userinfo.getUserId();
                String usercolor = userinfo.getUserColor();
                data.put("userid", userid);
                data.put("usercolor", usercolor);
    mysocket.emit("JOINROOM", data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    
    ```
    
    소켓은 싱글톤 패턴을 이용해서 모든 액티비티에서 같은 소켓인터페이스를 사용할 수 있도록 구현했다. 대기실에 들어오면 서버와 클라이언트는 소켓을 이용해 db에 존재하는 해당 대기실의 정보를 유저에게 전달한다. 각 유저의 앱에서는 대기실에 다른 유저들의 캐릭터와 아이디를 볼 수 있다.
    
    - 두더지 게임
        
        Delete를 누르게 되면 ViewHolder의 TextView에 있는 name과 number를 string으로 가져온다. 그 후 해당 name의 id를 찾는다. cursor와 for loop을 사용하며 Textview의 name과 Contacts.db의 이름을 비교한다. 같은 값을 찾게 되면 db 테이블에서 삭제한다.
        
        ```java
        case R.id.delete:
                            //activity.getContentResolver().delete(Uri.parse(MainActivity.PROVIDER_URI),"name="+name.getText().toString() ,null);
        
                            String[] columns = new String[]{"_id", "name", "phone"};
                            Cursor c = activity.getContentResolver().query(Uri.parse(MainActivity.PROVIDER_URI),columns, null,null,null,null);
                            int id = 0;
        
                            if(c != null){
                                while(c.moveToNext()){
                                    String find_name = c.getString(1);
                                    id = c.getInt(0);
                                    System.out.println(find_name);
                                    System.out.println(mname);
                                    if(mname.equals(find_name)){
                                        System.out.println("it's same\n");
                                        //System.out.println(id);
                                        break;
                                    }
                                }
                            }
                            activity.getContentResolver().delete(Uri.parse(MainActivity.PROVIDER_URI),"_id ="+ String.valueOf(id) ,null);
                            Intent intent1 = new Intent(context, MainActivity.class);
                            activity.startActivity(intent1);
                            break;
                    }
        ```
        
    
    두더지게임은 9마리의 두더지로 구성되며, 각 두더지는 thread로 동작한다. 모두 랜덤함수를 사용해서 움직이며, x,y축 translation 하는 애니메이션을 사용한다. 내부에는 하나의 스레드를 더 사용한 stop watch가 존재하며 30초가 끝나면 score가 userinfo에 저장된다. userinfo도 마찬가지로 싱글톤 패턴으로 유저의 id, 색상, 스코어를 기록한다. 각 유저들의 스코어는 결과화면에서 서버의 데이터 테이블로 전송되며 sorting되어 유저들의 화면에 출력한다.
    
    ```jsx
    public AnimatorSet moleAction(ImageButton mole, Integer speed) {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator up = ObjectAnimator.ofFloat(mole, "translationY", -140);
            ObjectAnimator down = ObjectAnimator.ofFloat(mole, "translationY", 0);
            animatorSet.play(up).before(down);
            animatorSet.setDuration(speed);
            return animatorSet;
    ```
    
    - 결과 화면
        
        버튼을 클릭하면 안드로이드에서 제공하는 기본 서비스를 사용하여 전화와 메세지를 할 수 있다.
        
        ```java
        case R.id.callbutton:
                            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CALL);
                            }
                            context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+m_number)));
                            break;
                        case R.id.messagebutton:
                            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SMS);
                            }
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"+m_number)));
                            break;
        ```
        
    
    룰렛이 돌아가며 벌칙이 정해진다.
    
    ```jsx
    private void setRouletteView(RecyclerView roulette, ArrayList<String> list) {
            roulette.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myView.getContext());
            roulette.setLayoutManager(linearLayoutManager);
            Roulette_RecyclerAdapter adapter = new Roulette_RecyclerAdapter(setRouletteContents(list));
            roulette.setAdapter(adapter);
            LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(roulette);
    
            roulette.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) { return true; }
            });
        }
    
        private void setRouletteAction(RecyclerView roulette) {
            recursiveTimer(roulette,2000,30).start();
        }
    
        private ArrayList<String> setRouletteContents(ArrayList<String> list) {
            ArrayList<String> returnList = new ArrayList<>();
    
            list.add("병샷");
            list.add("앞구르기 한바퀴");
            list.add("딱밤한대");
            list.add("노래한곡");
            list.add("사장님께 인사하고오기");
            list.add("옆자리에서 술얻어먹기");
            list.add("안주하나 사기");
    ```
