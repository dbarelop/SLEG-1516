CC=cobc

all: hrm_backup

%: %.cbl
	$(CC) -x $^ -o $@

clean:
	rm hrm_backup
