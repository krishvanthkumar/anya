package com.example.medical_application;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE = 0;
    private static final int VIEW_TYPE_OPTION = 1;

    String ans ,qns;

    private List<String> messages;
    private List<String> options;
    private Context context;

    public ChatAdapter(Context context, List<String> messages, List<String> options) {
        this.context = context;
        this.messages = messages;
        this.options = options;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < messages.size()) {
            return VIEW_TYPE_MESSAGE;
        } else {
            return VIEW_TYPE_OPTION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case VIEW_TYPE_MESSAGE:
                view = inflater.inflate(R.layout.messageitem, parent, false);
                return new MessageViewHolder(view);
            case VIEW_TYPE_OPTION:
                view = inflater.inflate(R.layout.messageitem2, parent, false);
                return new OptionViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageViewHolder) {
            ((MessageViewHolder) holder).bind(messages.get(position));
        } else if (holder instanceof OptionViewHolder) {
            ((OptionViewHolder) holder).bind(options.get(position - messages.size()));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size() + options.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.textmsg);
        }

        void bind(String message) {
            messageText.setText(message);
        }
    }

    class OptionViewHolder extends RecyclerView.ViewHolder {
        Button optionButton;

        OptionViewHolder(View itemView) {
            super(itemView);
            optionButton = itemView.findViewById(R.id.option_button);
        }

        void bind(String option) {
            optionButton.setText(option);
            optionButton.setOnClickListener(view -> handleOptionClick(option));
        }

        private void handleOptionClick(String option) {
            Map<String, List<String>> questionAnswerMap = getQuestionAnswerMap();
            List<String> questions = questionAnswerMap.get(option);
            System.out.println(questionAnswerMap);
            System.out.println(questions);
            if (questions != null && !questions.isEmpty()) {
                options.clear();
                messages.add(option);
                qns=option;
                System.out.println(qns);
                fetchData(qns,questions);
                System.out.println(ans);

            } else {
                options.clear();
                messages.add(option);
                qns=option;

                System.out.println(qns);
                fetchData(qns,questions);
                System.out.println(ans);



            }
        }


        private Map<String, List<String>> getQuestionAnswerMap() {
            Map<String, List<String>> questionAnswerMap = new HashMap<>();
            questionAnswerMap.put("Emotions, Mood Swings, Mental Health", Arrays.asList(
                    "Is it normal to feel overwhelmed or anxious after giving birth?",
                    "When should I seek help for postpartum depression?",
                    "How can I cope with the emotional challenges of motherhood?",
                    "How can I deal with sleepless nights? Is it affecting my mental health?",
                    "Why am I struggling to bond with my baby? Will it improve over time?",
                    "Why does it feel like my husband/partner does not understand what I am going through?",
                    "Why do I feel frustrated and irritable when my baby is crying? How do I manage these emotions better?",
                    "Am I a bad mother for not being able to calm/soothe my crying baby?",
                    "What are the signs and symptoms of postpartum depression?",
                    "When is the right time to consult the doctor?",
                    "What role do hormones play in postpartum depression?",
                    "At what point do mood swings become a concern, and when should I seek professional help?",
                    "Is it normal to talk about mood swings with friends and family, or is it something you should hide?"
            ));

            questionAnswerMap.put("Newborn Care Sleep", Arrays.asList(
                    "How can I establish a sleep routine for my baby?",
                    "What are some tips for soothing a crying baby?",
                    "Is it normal for a baby to cry a lot?",
                    "How can I help my older child adjust to having a new sibling?"
            ));
            questionAnswerMap.put("Relationship Support", Arrays.asList(
                    "How can I maintain a strong relationship with my partner after having a baby?",
                    "What can friends and family do to support me during the postpartum period?",
                    "I feel changes in my friendships since becoming a mother. Is this normal? How can I maintain them?",
                    "How to discuss and share parenting responsibilities with my partner and family members?"
            ));
            questionAnswerMap.put("Physical Recovery", Arrays.asList(
                    "How long does it take for postpartum bleeding to stop?",
                    "What are some effective ways to manage postpartum pain?",
                    "When can I start exercising again after giving birth?",
                    "Are there any exercises to help tone my abdominal muscles after childbirth?"
            ));
            questionAnswerMap.put("Breastfeeding", Arrays.asList(
                    "How do I know if my baby is latching properly?",
                    "What can I do to increase my milk supply?",
                    "Are there any foods I should avoid while breastfeeding?",
                    "How often should I breastfeed my baby?",
                    "How can I manage breastfeeding when returning to work?"
            ));
            questionAnswerMap.put("Things to do when you are feeling stressed", Arrays.asList(
                    "Deep Breathing Exercises", "Mindfulness Meditation", "Journaling", "Relaxation Techniques","Self-Compassion Practices",
                    "Creative Outlets","Aromatherapy","Warm Baths","Positive Affirmations","Gentle Exercise","Listen to Calming Music","Establish a Daily Routine",
                    "Connect with Nature","Mindful Eating"
            ));

            return questionAnswerMap;
        }
        private void fetchData(String qns,List<String> questions) {
            String apiUrl = ipforall.ipt + "chatbot.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            handleResponse(response,questions);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", "Error: " + error.toString());
                    handleError(error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("qns", qns);
                    Log.d("Volley Request", "Params: " + data.toString());
                    return data;
                }
            };

            // Add the request to the RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }


        private void handleResponse(String response,List<String> questions) {
            Log.d("JSON Response", response);
            System.out.println(response);
            ans = response;
            System.out.println(1);
            System.out.println(questions);
            if (ans.substring(1, ans.length() - 1).equals("hi")) {
                options.addAll(questions);
            } else {
                ans=ans.substring(1, ans.length() - 1);
                System.out.println(ans);
                //ans.replace("\r","");
                messages.add(ans);
                options.addAll(Arrays.asList(
                        "Emotions, Mood Swings, Mental Health",
                        "Newborn Care Sleep",
                        "Relationship Support",
                        "Physical Recovery",
                        "Breastfeeding",
                        "Things to do when you are feeling stressed"
                ));
            }
            notifyDataSetChanged();
        }

        private void handleError(VolleyError error) {
            Log.e("Volley Error", "Error: " + error.toString());
            System.out.println("boooooo");
        }
    }
}
