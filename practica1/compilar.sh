source=$1
cobc $source -o ${source%.*} -x && ./${source%.*}
