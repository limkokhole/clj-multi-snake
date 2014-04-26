(ns multi-snake.game
  (:use [multi-snake.board :as ms.b]
        [multi-snake.input :as ms.in]))

(defn- pos-in-dir
  "Translate a point one unit in the provided direction.
  N.B. Origin is top-left."
  [{:keys [x y]} dir]
  (cond
    (= :right dir) {:x (inc x) :y y}
    (= :left  dir) {:x (dec x) :y y}
    (= :down  dir) {:x x :y (inc y)}
    (= :up    dir) {:x x :y (dec y)}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Public API
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn tick
  "Takes a gamestate and moves it one unit-of-time forward, returning the
  resultant gamestate."
  [game]
  (let [{pos :player-pos dir :player-dir} game
        action (get-action (:input game))
        dir' (if action action dir)
        pos' (pos-in-dir pos dir')]
    (assoc game :player-pos pos' :player-dir dir')))

(defn game
  "Generates a game with the given configuration. Default values will be used
  for any that aren't provided."
  ([] (game {}))
  ([config]
   (let [player-pos (or (:starting-pos config) {:x 0 :y 0})
         player-dir (or (:starting-dir config) :right)
         board (or (:board config) (ms.b/board))
         input (or (:input config) (ms.in/basic-input))]
     {:player-pos player-pos
      :player-dir player-dir
      :board board
      :input input})))

