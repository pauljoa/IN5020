# You can uncomment the following lines to produce a png figure
set terminal png enhanced
set output 'plot.png'

set title "Average Clustering Coefficient"
set xlabel "cycles"
set ylabel "clustering coefficient (log)"
set key right top
set logscale y 
plot "ccr30.txt" title 'Random Graph c = 30' with lines, \
	"ccs30.txt" title 'Shuffle c = 30' with lines, \
	"ccr50.txt" title 'Random Graph c = 50' with lines, \
	"ccs50.txt" title 'Shuffle c = 50' with lines