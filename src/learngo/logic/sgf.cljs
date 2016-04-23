(ns learngo.logic.sgf)

(defn parse-coord [coord]
  (- (.charCodeAt coord 0)
     (.charCodeAt "a" 0)))

(defn parse-move [move]
  [(parse-coord (nth move 1))
   (parse-coord (nth move 2))])

(defn parse-game [sgf]
  (->> sgf
       (re-seq #"\[..\]")
       (map parse-move)))

(def shusaku-vs-shuwa
  ";B[qd];W[dc];B[pq];W[oc];B[cp];W[qo];B[pe];W[eq];B[nd];W[mc]
;B[qk];W[np];B[po];W[pp];B[op];W[qp];B[oq];W[oo];B[pn];W[qq]
;B[qr];W[rr];B[mq];W[no];B[pr];W[rm];B[pl];W[kq];B[lp];W[nm]
;B[kp];W[jp];B[rs];W[sr];B[rl];W[qm];B[pm];W[kn];B[jq];W[iq]
;B[jr];W[ip];B[nk];W[ni];B[ir];W[hr];B[kr];W[pj];B[pk];W[ph]
;B[sp];W[qn];B[sm];W[sn];B[sl];W[mk];B[ml];W[nl];B[mj];W[nj]
;B[lk];W[ok];B[so];W[mk];B[rn];W[md];B[nk];W[mf];B[pc];W[de]
;B[cm];W[dl];B[dm];W[fl];B[he];W[hg];B[hc];W[ff];B[kc];W[id]
;B[ic];W[kd];B[le];W[me];B[ec];W[eb];B[fc];W[fb];B[gb];W[ed]
;B[dk];W[fd];B[ld];W[ke];B[lc];W[lf];B[hd];W[ie];B[if];W[jf]
;B[lb];W[lm];B[el];W[fk];B[di];W[ob];B[pb];W[pa];B[mb];W[od]
;B[qg];W[ej];B[ch];W[gc];B[oe];W[nb];B[jd];W[je];B[jg];W[jc]
;B[jb];W[hf];B[qh];W[ng];B[ol];W[mk];B[ne];W[nc];B[nk];W[oj]
;B[ln];W[lo];B[ko];W[mn];B[in];W[em];B[gq];W[ek];B[ck];W[jn]
;B[gr];W[go];B[fo];W[dp];B[gn];W[im];B[hm];W[hl];B[gl];W[hk]
;B[cq];W[en];B[co];W[ho];B[fp];W[hb];B[ib];W[ga];B[ka];W[bg]
;B[il];W[jm];B[fn];W[hn];B[qa];W[bh];B[do];W[bi];B[ci];W[gm]
;B[cg];W[cf];B[bj];W[hq];B[hs];W[qj];B[rj];W[qi];B[ri];W[eg]
;B[og];W[oh];B[nf];W[pg];B[pf];W[ig];B[oa];W[na];B[ma];W[dg]
;B[mg];W[of];B[bf];W[be];B[og];W[nh];B[ei];W[fi];B[cl];W[eo]
;B[ep];W[gp];B[fq];W[fm];B[lg];W[li];B[pa];W[ge];B[ai];W[dj]
;B[cj];W[dn];B[cn];W[nq];B[nr];W[mp];B[on];W[jo];B[lq];W[nn]
;B[eh];W[fh];B[ia];W[ha];B[of];W[el];B[jd])
")
