package com.nadia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.nadia.Gender.F;
import static com.nadia.Gender.M;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest {

    List<User> users = new ArrayList<>();
    private User nadia;
    private User sofia;
    private User guigui;

    @BeforeEach
    void setUp() {


        users.add(UserFactory.female(1, "nadia", Arrays.asList("admin"), LocalDate.parse("1981-05-24")));
        users.add(UserFactory.female(2, "sofia", Arrays.asList("manager"), LocalDate.parse("2013-09-23")));
        users.add(new UserBuilder().id(3).name("guillaumE").roles(Arrays.asList("commerciale")).birthDate(LocalDate.parse("1993-09-07")).gender(M).createUser());
        users.add(UserFactory.female(4, "lyna", Arrays.asList("dev"), LocalDate.parse("2021-07-04")));
        users.add(UserFactory.female(5, "zohra", Arrays.asList("lead"), LocalDate.parse("1954-01-01")));
        users.add(UserFactory.female(6, "aicha", Arrays.asList("dev"), LocalDate.parse("1983-05-24")));
        users.add(UserFactory.female(7, "wahiba", Arrays.asList("admin"), LocalDate.parse("1979-05-24")));
        users.add(new UserBuilder().id(8).name("yacine").roles(Arrays.asList("dev")).birthDate(LocalDate.parse("1991-05-24")).gender(M).createUser());
        users.add(UserFactory.female(9, "lydia", Arrays.asList("admin"), LocalDate.parse("2013-05-24")));
        users.add(UserFactory.female(10, "amandine", Arrays.asList("lead"), LocalDate.parse("2021-05-24")));

        nadia = users.get(0);
        nadia.forgetToSing();

        sofia = users.get(1);
        guigui = users.get(2);
    }

    @Test
    void findAllRoles() {
        Set<String> roles = users.stream()
                .map(User::getRoles)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
//        Set<String> roles = new HashSet<>();
//        users.forEach(user -> {
//            for (String role : user.getRoles()) {
//                roles.add(role);
//            }
//        });
//        for (String role : roles) {
//            System.out.println(role);
//        }
        assertThat(roles)
                .hasSize(5)
                .isNotNull()
                .isNotEmpty()
                .contains("dev")
                .containsExactlyInAnyOrder("manager", "dev", "commerciale", "admin", "lead");


    }

    @Test
    void FindAllUserByrole() {
        // declarative
        List<User> sortedUsers = users.stream()
                .sorted(Comparator.comparing(user -> user.getRoles().get(0)))
                .collect(Collectors.toList());

//        List<User> sortedUsers = new ArrayList<>(users);

//        sortedUsers.sort(Comparator.comparing(user -> user.getRoles().get(0)));

//        sortedUsers.sort(Comparator.comparing(User::getName));
//        sortedUsers.sort((user1, user2) -> user1.getName().compareTo(user2.getName()));

        for (User sortedUser : sortedUsers) {
            System.out.println(sortedUser);
        }

        assertThat(sortedUsers).endsWith(users.get(1));

    }

    @Test
    void findById() {

        Optional<User> u = users.stream()
                .filter(user -> user.getId().equals(3))
                .findAny();

        assertThat(u).isPresent()
                .hasValue(users.get(2));
    }

    @Test
    void mapUser() {
//        Map<Integer, User>  mapUsers = new HashMap<>();
//        for (User user : users) {
//            mapUsers.put(user.getId(), user);
//        }

        Map<Integer, User> mapUsers = users.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        Function.identity() // etant donné un user, renvoit le user (user -> user)
                ));

        User user = mapUsers.get(1);
        User user4 = mapUsers.get(5);

        assertThat(user).isEqualTo(users.get(0));
        assertThat(user4).isEqualTo(users.get(4));
        assertThat(mapUsers).hasSize(users.size());
    }

    @Test
    void transform() {

        // users -> name en majuscule triées
        List<String> listUserName = users.stream().map(User::getName)
                .sorted()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
//        System.out.println(listUserName);

        // users -> capitalize
        List<String> capitalized = users.stream().map(User::getName)
                .sorted()
                .map(this::capitalize)
                .collect(Collectors.toList());
        System.out.println(capitalized);
    }

    @Test
    void allAge() {
        LocalDate date = LocalDate.now();
        users.stream().map(User::getBirthDate)
                .map(birthDate -> birthDate.until(date))
                .map(Period::getYears)
                .forEach(System.out::println);

    }

    @Test
    void getMinorUser() {
        LocalDate now = LocalDate.now();
        List<User> minors = users.stream()
                .filter(user -> user.getAge(now) < 18)
                .collect(Collectors.toList());

        System.out.println("minors = " + minors);
    }

    @Test
    void youngUser() {
        LocalDate now = LocalDate.now();
//        User user = users.get(0);
//        int minAge = user.getAge(now);
//        for (int i = 1; i < users.size(); i++) {
//            User u = users.get(i);
//            int age = u.getAge(now);
//            if (age < minAge) {
//                user = u;
//                minAge = age;
//            }
//        }

        Optional<User> younger = users.stream()
                .min(Comparator.comparing(u -> u.getAge(now)));

        assertThat(younger)
                .isPresent()
                .hasValue(users.get(3));
    }

    @Test
    void average() {
        int sum = 0;
        LocalDate now = LocalDate.now();
        for (User user : users) {
            sum += user.getAge(now);
        }
        double average = 1.0 * sum / users.size();
        System.out.println(average);

        double mean = users.stream()
                .mapToInt(user -> user.getAge(now))
                .average()
                .orElse(0);

        System.out.println("mean = " + mean);


    }

    @Test
    void gender() {
        List<User> genders = users.stream().filter(user -> user.getGender().equals(F))
                .collect(Collectors.toList());
        assertThat(genders).hasSize(8);
    }

    @Test
    void mixGender() {
        List<User> femaleGenders = users.stream()
                .filter(user -> user.getGender().equals(F))
                .collect(Collectors.toList());
        List<User> maleGenders = users.stream()
                .filter(user -> user.getGender().equals(M))
                .collect(Collectors.toList());
        List<User> answers = new ArrayList<>();

        Iterator<User> females = femaleGenders.iterator();
        Iterator<User> males = maleGenders.iterator();

        while (females.hasNext() || males.hasNext()) {
            if (females.hasNext()) {
                answers.add(females.next());
            }
            if (males.hasNext()) {
                answers.add(males.next());
            }
        }

        answers.forEach(System.out::println);

    }

    @Test
    void differentAge() {
        User guigui = users.stream()
                .filter(user -> user.getName().equalsIgnoreCase("guillaume"))
                .findAny().get();

        users.stream()
                .filter(user -> !user.equals(guigui))
                .map(user -> {
                    int years = guigui.getBirthDate()
                            .until(user.getBirthDate())
                            .getYears();
                    String moreOrLess = (years > 0 ? "plus" : "moins");
                    return "Guillaume a " + Math.abs(years) + " ans de " + moreOrLess + " que " + user.getName();
                })
                .forEach(System.out::println);
    }

    private String capitalize(String name) {
        return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase();
    }


    @Test
    void singersCanSing() {
        assertThat(nadia.sing()).isEqualTo("..");
        assertThat(sofia.sing()).isEqualTo("Je sais chanter");
        assertThat(guigui.sing()).isEqualTo("..");

        nadia.learnToSing();
        assertThat(nadia.sing()).isEqualTo("Je sais chanter");

        users.forEach(this::doSing);

        new UserBuilder().createUser()
                .name("nadia")
                .roles(Arrays.asList("singer"))
                .singerAbility(new BadSinger())
                .birthDate(LocalDate.now());
    }

    @Test
    void setBeauty() {
        Map<Integer, List<User>> beautyUsers = new HashMap<>();
        Random r = new Random();

        for (User user : users) {
            Integer n = 1 + r.nextInt(10);
            user.setBeauty(n);
        }
        for (User user : users) {
            Integer beauty = user.getBeauty();
            if (!beautyUsers.containsKey(beauty)) {
                beautyUsers.put(beauty, new ArrayList<>());
            }
            beautyUsers.get(beauty).add(user);

        }
        for (Map.Entry<Integer, List<User>> entry : beautyUsers.entrySet()) {
            String s = entry.getKey() + "/10" + ": [" + entry.getValue().stream()
                    .map(User::getName)
                    .collect(Collectors.joining(", ")) + "]";
            System.out.println(s);
        }
    }

    @Test
    void setBeauty2() {
        Random r = new Random();

        for (User user : users) {
            Integer n = 1 + r.nextInt(10);
            user.setBeauty(n);
        }

        Map<Integer, List<User>> beautyUsers = users.stream()
                .collect(Collectors.groupingBy(
                        User::getBeauty
                ));
        for (Map.Entry<Integer, List<User>> entry : beautyUsers.entrySet()) {
            String s = entry.getKey() + "/10" + ": [" + entry.getValue().stream()
                    .map(User::getName)
                    .collect(Collectors.joining(", ")) + "]";
            System.out.println(s);
        }
    }


    @Test
    public void allHaveAverage() {

        Random r = new Random();

        for (User user : users) {
            int n = 1 + r.nextInt(10);
            user.setBeauty(Math.max(n, 5));
        }

    }

    @Test
    void probabilityOfBeingBeautifull() {
        Random r = new Random();
        int count = 0;
        int N = 2000;
        for (int i = 1; i<= N; i++){
            for (User user : users) {
                int n = 1 + r.nextInt(10);
                user.setBeauty(Math.max(n, 5));
                if (user.isBeautiful()){
                    count++;
                }

            }

        }
        double pribability =  1.0 * count / (users.size() * N);
        System.out.println("pribability = " + pribability);
    }

    @Test
    public void guiguiIsBeautifull(){
        guigui.setBeauty(10);
        assertThat(guigui.isBeautiful()).isTrue();
    }
    @Test
    public void guiguiIsNotBeautifull(){
        guigui.setBeauty(7);
        assertThat(guigui.isBeautiful()).isFalse();
    }

    void doSing(SingerAbility singerAbility) {
        System.out.println(singerAbility.sing());
    }
}