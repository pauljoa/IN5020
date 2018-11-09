# You can uncomment the following lines to produce a png figure
set terminal png enhanced
set output 'plot.png'

set title "Average Path Length"
set xlabel "cycles"
set ylabel "average path length (log)"
set key right top
set logscale y 
plot "plr30.txt" title 'Random Graph c = 30' with lines, \
	"pls30.txt" title 'Shuffle c = 30' with lines, \
	"plr50.txt" title 'Random Graph c = 50' with lines, \
	"pls50.txt" title 'Shuffle c = 50' with lines