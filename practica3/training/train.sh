#!/bin/bash

LANGUAGE=spa

echo "terminal 0 0 0 0 0" > font_properties

for f in *.png; do
	# Generate training file
	tesseract $f ${f/.png/} nobatch box.train
done

# Unicharset
unicharset_extractor *.box
# Clustering
shapeclustering -F font_properties -U unicharset *.tr
# Mftraining
mftraining -F font_properties -U unicharset -O $LANGUAGE.unicharset *.tr
# Cntraining
cntraining *.tr
# Renaming generated files
mv inttemp $LANGUAGE.inttemp
mv normproto $LANGUAGE.normproto
mv pffmtable $LANGUAGE.pffmtable
mv shapetable $LANGUAGE.shapetable
# Combining
combine_tessdata $LANGUAGE.


