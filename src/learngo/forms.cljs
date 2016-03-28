(ns learngo.forms)

(defn size-slider [id caption & {:keys [min max]
                                 :or {min 0
                                      max 19}}]
  [:div.row
   [:div.col-sm-4
    [:label {:for id} caption]]
   [:div.col-sm-4
    [:input.form-control {:field :range
                          :min min
                          :max max
                          :id id}]]
   [:div.col-sm-2
    [:label {:field :label :id id}]]])

(defn geometry []
  [:div
   (size-slider :size "Board size" :min 2)
   (size-slider :top "Top cutoff" :max 15)
   (size-slider :left "Left cutoff" :max 15)
   (size-slider :bottom "Bottom cutoff" :max 15)
   (size-slider :right "Right cutoff" :max 15)])

(defn input [id caption]
  [:div.row
   [:div.col-sm-4
    [:label {:for id} caption]]
   [:div.col-sm-4
    [:input.form-control {:field :text
                          :id id}]]])
