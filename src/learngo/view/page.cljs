(ns learngo.view.page
  (:require [clojure.string       :as str]
            [learngo.i18n         :as i18n]
            [learngo.navigation   :as navigation]
            [learngo.problems     :as problems]
            [learngo.state        :as state]
            [learngo.view.contact :as contact-view]
            [learngo.view.editor  :as editor-view]
            [learngo.view.intro   :as intro-view]
            [learngo.view.lessons :as lessons-view]
            [learngo.view.problem :as problem-view]
            [learngo.view.utils   :as ui]))

(defn home-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :welcome-text)]
   [:div {:class "row"}
    [:div {:class "col-sm-8 justify"}
     [:div.alert.alert-info
      (i18n/translate :under-construction)]]
     [:div {:class "col-sm-4"}
       [:button.btn.btn-wood.btn-block
        {:type :button :class "attention"
         :on-click #(navigation/go :tutorial)}
        [:span {:class "btn-header"}(i18n/translate :start-tutorial)]
        [:span {:class "btn-subtext"} (i18n/translate :start-tutorial-subtext)]]
       [:button.btn.btn-default.btn-block
         {:type :button
          :on-click #(navigation/go :about-go)}
         [:span {:class "btn-header"}(i18n/translate :start-about-go)]
         [:span {:class "btn-subtext"} (i18n/translate :start-about-go-subtext)]]]]])

(defn navbar [page-name & items]
  [:nav {:class "navbar navbar-default"}
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand page-name]]
    [:ul {:class "nav navbar-nav"}
     (doall
      (for [[item icon] items]
        [:li {:key item
              :class (when (= @state/current-page item)
                       "active")
              :style {:cursor :pointer}}
         [:a {:on-click #(navigation/go item)}
          (when icon
            [ui/glyphicon icon])
          " "
          (i18n/translate item)]]))]]])

#_(defn problem-page []
  [:div
   (if @intro?
     [intro-view/page {:on-next #(reset! intro? false)}]
     [problem-view/collection
      problems/all])])

(defn about-go-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :about-go)]
   [:br]
   [:div.alert.alert-info
    (i18n/translate :under-construction)]]
  #_[:div {:class "content"}
   [:h1 (i18n/translate :about-go)]
   [:img {:class "img-rounded flow-right" :src "graphics/external/gostobobo.gif"}]
   [:p {:class "justify"} "Go hat - unter dem Namen 'Wei-qi' - seinen Ursprung im alten China des zweiten vorchristlichen Jahrtausends und ist damit mehr als 4000 Jahre alt. Vor ungefähr 1300 Jahren hat Wei-qi seinen Weg nach Japan gefunden. Seitdem wurde die antike Form des Wei-qi von den Japanern zu der heute vornehmlich bekannten Form des Go verändert und weiterentwickelt. Auch heute haben Go bzw. Wei-qi in Japan und China einen großen Stellenwert, man findet kaum eine Zeitung oder ein Magazin ohne eine Spalte über das Spiel. Ebenso populär ist das Spiel unter dem Namen 'Baduk' in Korea. Außerhalb von China, Japan und Korea wird ebenfalls Go gespielt. Weltweit dürfte die Zahl der Go-Spieler mehrere Millionen betragen."]
   [:img {:class "img-rounded flow-left" :src "graphics/external/goplaying.jpg"}]
   [:p {:class "justify"} "Go fasziniert durch Einfachheit und Komplexität gleichermaßen. Es verfügt nur über wenige, leicht zu erlernende Regeln, bietet aber auf dieser Basis eine nahezu unbegrenzte Anzahl von Möglichkeiten. Während die Regeln jedem Interessierten in fünf Minuten erklärt werden können, reicht ein ganzes Menschenleben nicht aus, um die Tiefen des Spiels vollends zu ergründen. Selbst die professionellen Go-Spieler in Fernost spielen niemals in ihrem Leben zweimal die gleiche Partie. Die Zahl aller möglichen Züge ist nicht nur ungleich höher als beim Schach, sondern übersteigt sogar die von Einstein errechnete Anzahl der Atome im Universum."]
   [:img {:class "img-rounded flow-right" :src "graphics/external/home9.jpg"}]
   [:p {:class "justify"} "Die Regeln des Spiels sind von verblüffender Einfachheit. Gespielt wird Go mit schwarzen und weißen Steinen auf einem Brett mit 19x19 Linien. Es wird abwechselnd auf die Schnittpunkte der Linien gesetzt. Ziel ist das Abgrenzen von Gebiet - freien Schnittpunkten auf dem Brett -, wobei allerdings auch gegnerische Steine gefangen werden können. Gewonnen hat, wer am Ende mehr Gebiet abgegrenzt hat. Die beiden Spieler sind damit gleichsam Bauern, die ein Stück Land unter sich aufteilen, wobei jeder etwas mehr als der andere haben möchte. Es geht nicht darum, das ganze Land zu besitzen. Jeder von beiden will allein zeigen, daß er sich beim Verteilen etwas geschickter anstellt. Es geht nicht um die Vernichtung des Gegners, sondern um mindestens einen Punkt mehr am Ende der Partie. Nicht der totale Krieg, sondern der faire Vergleich von geistigen Fähigkeiten liegt dem Spiel als Idee zugrunde. »Kämpfen ist nicht der Schlüssel zum Go, es dient allein als letzter Ausweg.« (Zhong-Pu Liu, 1078 v. Chr.)"]
   [:img {:class "img-rounded flow-left" :src "graphics/external/shinno.jpg"}]
   [:p {:class "justify"} "In Japan wird Go von über zehn Millionen Menschen gespielt. Es gibt dort unzählige kleine Go-Clubs, Spielgruppen an Schulen und Universitäten und regelmäßig Go-Turniere  für Amateure und Profis. Mit der japanischen Kulturtradition ist Go ebenso verbunden wie Sumo-Ringen, die Tee-Zeremonie, Kendo oder die Kunst des Bogenschießens. Zu Beginn des 17. Jahrhunderts  - zu Beginn der Edo-Periode (1600-1868) - wurden in Japan staatlich unterstützte Go-Schulen gegründet, deren Mitglieder jährlich in sogenannten 'Castle-Games' in Anwesenheit des amtierenden Shoguns um die Vorherrschaft im Go wetteiferten. Im 20. Jahrhundert bildete sich - mit dem Ende der großen Go-Schulen - ein  nationaler Go-Verband heraus, unter dessen Führung nun die großen Turniere des Landes ausgetragen werden. In den vergangenen beiden Jahrzehnten kam es zu einer verstärkten Zusammenarbeit mit China und Korea. Während sich 'Wei-qi' in China erst von der Kulturrevolution erholen mußte - Go-Spieler wurden zu dieser Zeit in China als 'Intellektuelle' verfolgt -, ist 'Baduk' in Korea so populär wie Fußball oder Skat in Deutschland. Nicht zuletzt auf der Basis dieser Zusammenarbeit gibt es seit einigen Jahren Weltmeisterschaften im Go - sowohl für Amateure als auch für Profis. An den Weltmeisterschaften für Amateure nehmen jedes Jahr Vertreter aus über 60 Ländern, darunter aus vielen europäischen Nationen, teil."]
   [:img {:class "img-rounded flow-right" :src "graphics/external/igocard_f1.jpg"}]
   [:p {:class "justify"} "In Deutschland gibt es über 30.000 Go-Spieler. Davon haben sich 1.700 in Clubs organisiert. Zur Zeit der Weimarer Republik hat sich die erste Go-Gruppe in Berlin gebildet, die sich unregelmäßig in einem Café traf. Heute findet sich in nahezu jeder größeren deutschen Stadt ein Go-Club mit regelmäßigem Spielbetrieb. An nahezu jedem Wochenende wird in einer deutschen Stadt ein Go-Turnier veranstaltet. Außerdem finden jedes Jahr deutsche Meisterschaften ebenso wie Europameisterschaften statt. Bei den vielen regelmäßigen Spielabenden in den einzelnen Städten sind Anfänger und Interessierte gerngesehene Gäste. Man kann dort die Regeln erklärt bekommen und versuchen, die Tiefen des Spiels zu ergründen. Eine Liste der Spielabende in Deutschland findet sich auf der auf dieser Web-Site.
Für alle, die nun Interesse bekommen haben und die Go-Regeln sofort erklärt bekommen möchten, findet sich auf dieser Web-Site ein kleiner Go-Kurs mit fünf Lektionen. Wer diese aufmerksam studiert, sollte danach in der Lage sein, mit dem Spielen zu beginnen. Ganz ambitionierte können auch die mit den Studium der japanischen und chinesischen Go-Regeln fortsetzen."]
  ])

(def github-url "github.com/go-de/learn-go")

(defn github-link [text]
  [:a {:href (str "https://" github-url)}
   text])

(defn contribute-page []
  [:div
   [:h1 (i18n/translate :contribute)]
   [:ul
    [:li
     [github-link (i18n/translate :fork-us-on-github)]]
    [:li
     [:p (i18n/translate :or-build-and-send-a-problem) ":"]]]
   [:h3 (i18n/translate :problem-editor)]
   [editor-view/editor {:size 9}]])

(def links-list
  [["dgob.de" (i18n/translate :dgob-info)]
   ["pandanet-igs.com" (i18n/translate :pandanet-info)]
   ["hebsacker-verlag.de" (i18n/translate :hebsacker-info)]
   ["go-baduk-weiqi.de" (i18n/translate :go-baduk-weiqi-info)]])

(defn links-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :useful-links)]
   [:br]
   (->> links-list
        (mapcat (fn [[url text]]
                  [[:dt>a {:href (str "http://" url)} url]
                   [:dd text]]))
        (into [:dl.dl-horizontal.link-list]))])

(defn contact-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :contact-us)]
   [:br]
   [:p (i18n/translate :contact-credits)]
   [:br]
   [:p (i18n/translate :email) ": " [contact-view/email]]
   [:p "Github: " [github-link github-url]]])

(defn footer []
  [:footer.footer
   [:div.row
    [:div.col-lg-4 [:p "Powered by "
                    [:a {:href "https://github.com/clojure/clojurescript"}
                     "ClojureScript"]
                    ", "
                    [:a {:href "https://github.com/reagent-project/reagent"}
                     "Reagent"]
                    ", and "
                    [:a {:href "http://wgo.waltheri.net/"}
                     "WGo.js"]]]
    [:div.col-lg-4 [github-link "Github"]]
    [:div.col-lg-4 [:a {:on-click (fn []
                                    (navigation/go :contact))}
                    (i18n/translate :contact-us)]]]])

(defn content []
  [:div
   [navbar (i18n/translate :learn-go)
    [:home :home]
    [:tutorial :education]
    [:about-go :question-sign]
    [:links :link]
    [:contribute :scissors]
    [:contact :envelope]]
   [:div
    (case @state/current-page
      :home [home-page]
      :tutorial [lessons-view/component]
      :about-go [about-go-page]
      :contribute [contribute-page]
      :links [links-page]
      :contact [contact-page]
      [:h1 "Page not found"])]
   [footer]])
