all: src/**/*.java
	mkdir -p bin1
	javac -d bin1 -cp src/cnn:bin/ src/**/*.java

test:
	java -cp bin1 cnn.driver.Main

clean:
	rm -rf bin1