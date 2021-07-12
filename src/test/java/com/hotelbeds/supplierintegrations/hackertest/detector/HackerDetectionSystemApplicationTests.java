package com.hotelbeds.supplierintegrations.hackertest.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.config.HackerDetectionSystemApplicationConfig;
import com.hotelbeds.supplierintegrations.hackertest.detector.utils.parser.LogLineParserException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


@RunWith( SpringRunner.class )
@SpringBootTest(classes= HackerDetectionSystemApplicationConfig.class)
class HackerDetectionSystemApplicationTests {

	@Autowired
	private HackerDetectorImpl hackerDetectorImpl;

	@Test
	public void emptyLogLine() {
		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine("");
		});

		String expectedMessage = "Please check the input line as its value is null or is empty";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));


	}

	@Test
	public void nullLogLine() {
		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine(null);
		});

		String expectedMessage = "Please check the input line as its value is null or is empty";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void lineWithSemicolon() {
		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine("80.238.9.179;133612947;SIGNIN_SUCCESS;Will.Smith");
		});

		String expectedMessage = "Line received doesn't have the proper format: 80.238.9.179;133612947;SIGNIN_SUCCESS;Will.Smith";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void lineWithMoreThan4Fields() {

		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith,field5");
		});

		String expectedMessage = "80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith,field5";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void lineWithWrongIp() {
		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine("80.238.9.179213,133612947,SIGNIN_SUCCESS,Will.Smith");
		});

		String expectedMessage = "Ip format is not valid. Got: 80.238.9.179213";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void lineWithWrongEpoch() {
		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine("80.238.9.179,wrongEpoch,SIGNIN_SUCCESS,Will.Smith");
		});

		String expectedMessage = "EpochTime not Valid. Current value is: wrongEpoch";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void lineWithWrongAction() {
		Exception exception = assertThrows(LogLineParserException.class, () -> {
			hackerDetectorImpl.parseLine("80.238.9.179,133612947,WRONG_ACTION,Will.Smith");
		});

		String expectedMessage = "Action not valid. Current value is: WRONG_ACTION";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void oneLineSuccessLog() {
		String result = hackerDetectorImpl.parseLine("80.238.9.179,133612947,SIGNIN_SUCCESS,Will.Smith");
		assertThat(result).isNull();
	}

	@Test
	public void fiveLinesFailLogWithinFiveMinutes() {
		String result = hackerDetectorImpl.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith");
		String result2 = hackerDetectorImpl.parseLine("80.238.9.179,133612948,SIGNIN_FAILURE,Will.Smith");
		String result3 = hackerDetectorImpl.parseLine("80.238.9.179,133612949,SIGNIN_FAILURE,Will.Smith");
		String result4 = hackerDetectorImpl.parseLine("80.238.9.179,133612950,SIGNIN_FAILURE,Will.Smith");
		String result5 = hackerDetectorImpl.parseLine("80.238.9.179,133612951,SIGNIN_FAILURE,Will.Smith");

		assertThat(result).isNull();
		assertThat(result2).isNull();
		assertThat(result3).isNull();
		assertThat(result4).isNull();
		assertThat(result5).isEqualTo("80.238.9.179");

	}

	@Test
	public void fiveLinesFailLogMoreThanFiveMinutes() {
		String result = hackerDetectorImpl.parseLine("80.238.9.179,133612947,SIGNIN_FAILURE,Will.Smith");
		String result2 = hackerDetectorImpl.parseLine("80.238.9.179,133613000,SIGNIN_FAILURE,Will.Smith");
		String result3 = hackerDetectorImpl.parseLine("80.238.9.179,133613060,SIGNIN_FAILURE,Will.Smith");
		String result4 = hackerDetectorImpl.parseLine("80.238.9.179,133613120,SIGNIN_FAILURE,Will.Smith");
		String result5 = hackerDetectorImpl.parseLine("80.238.9.179,134613120,SIGNIN_FAILURE,Will.Smith");

		assertThat(result).isNull();
		assertThat(result2).isNull();
		assertThat(result3).isNull();
		assertThat(result4).isNull();
		assertThat(result5).isNull();
	}

}
