package movies;

import java.math.BigDecimal;
import java.util.Optional;

public enum Type {
    ACTION{
        @Override
        public BigDecimal price() {
            return new BigDecimal(10);

        }

        @Override
        public Optional<Integer> minimumAge() {
            return Optional.of(16);
        }
    }, ADVENTURE {
        @Override
        public BigDecimal price() {
            return new BigDecimal(8);
        }

        @Override
        public Optional<Integer> minimumAge() {
            return Optional.of(12);
        }
    }, COMEDY {
        @Override
        public BigDecimal price() {
            return new BigDecimal(7);
        }

        @Override
        public Optional<Integer> minimumAge() {
            return Optional.empty();
        }
    };

    public abstract BigDecimal price();
    public abstract Optional<Integer> minimumAge();

    /*public Optional<Integer> minimumAge() {
        switch (this) {
            case ACTION:
                return Optional.of(16);
            case ADVENTURE:
                return Optional.of(12);
            default:
                return Optional.empty();

        }
    }*/

//    public BigDecimal price() {
//        switch (this) {
//            case ACTION:
//                return new BigDecimal(10);
//            case ADVENTURE:
//                return new BigDecimal(8);
//            case COMEDY:
//                return new BigDecimal(7);
//            default:
//                return BigDecimal.ZERO;
//        }
//    }
}

